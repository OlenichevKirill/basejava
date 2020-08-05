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

    public <T> T execute(String sql, String uuid, Executor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            System.out.println("SQLException message:" + e.getMessage());
            System.out.println("SQLException SQL state:" + e.getSQLState());
            System.out.println("SQLException SQL error code:" + e.getErrorCode());
            throw getException(e, uuid);
        }
    }

    private static StorageException getException(SQLException e, String uuid) {
        if (e.getSQLState().equals("23505")) {
            return new ExistStorageException(uuid);
        } else {
            return new StorageException(e);
        }
    }
}
