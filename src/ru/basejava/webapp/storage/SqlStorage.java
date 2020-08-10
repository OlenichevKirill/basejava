package ru.basejava.webapp.storage;

import ru.basejava.webapp.exception.NotExistStorageException;
import ru.basejava.webapp.model.ContactType;
import ru.basejava.webapp.model.Resume;
import ru.basejava.webapp.sql.SqlHelper;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;
    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContacts(resume, conn);
            insertContacts(resume, conn);

            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }

                    insertContacts(resume, conn);

                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                fillResume(ps, rs -> addContact(resume, rs));
            }
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume r where r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(uuid) from resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> resumeMap = new HashMap<>();
        return sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * from resume ORDER BY full_name, uuid")) {
                fillResume(ps, (rs) -> resumeMap.put(rs.getString("uuid"), new Resume(rs.getString("uuid"), rs.getString("full_name"))));
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * from contact")) {
                fillResume(ps, (rs) -> {
                    String resumeOUID = rs.getString("resume_uuid");
                    for (Map.Entry<String, Resume> map : resumeMap.entrySet()) {
                        if (map.getValue().getUuid().equals(resumeOUID)) {
                            addContact(map.getValue(), rs);
                        }
                    }
                });
            }
            List<Resume> list = new ArrayList<>(resumeMap.values());
            list.sort(RESUME_COMPARATOR);
            return list;
        });
    }

    private void insertContacts(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        ContactType type = ContactType.valueOf(rs.getString("type"));
        resume.addContact(type, value);
    }

    private void deleteContacts(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact where resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ps.executeUpdate();
        }
    }

    public interface FilledResume {
        void fill(ResultSet rs) throws SQLException;
    }

    private void fillResume(PreparedStatement ps, FilledResume resume) throws SQLException {
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            resume.fill(rs);
        }
    }
}
