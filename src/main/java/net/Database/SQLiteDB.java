package net.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class SQLiteDB {
    private final Connection connection;

    public SQLiteDB(Connection connection) {
        Objects.requireNonNull(connection);
        this.connection = connection;
    }

    public SQLiteDB(String filePath) {
        Objects.requireNonNull(filePath);

        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
