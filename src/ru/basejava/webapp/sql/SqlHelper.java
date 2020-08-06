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

    private static StorageException getException(SQLException e) {
        return e.getSQLState().equals("23505") ? new ExistStorageException(e) : new StorageException(e);
    }
}
