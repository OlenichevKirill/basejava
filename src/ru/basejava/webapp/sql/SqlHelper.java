package ru.basejava.webapp.sql;

import ru.basejava.webapp.exception.ExistStorageException;
import ru.basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public interface Execute<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

    public <T> T execute(String sql, Execute<T> execute) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return execute.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <T> void executeSave(String sql, String uuid, Execute<T> execute) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            execute.execute(ps);
        } catch (SQLException e) {
            throw new ExistStorageException(uuid);
        }
    }
}
