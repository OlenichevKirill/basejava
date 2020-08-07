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

    public interface Executor<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

    public <T> T execute(String sql, Executor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw getException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw getException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    private static StorageException getException(SQLException e) {
        return e.getSQLState().equals("23505") ? new ExistStorageException(e) : new StorageException(e);
    }
}
