package at.fhtw.mcg.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum DatabaseManager {
    INSTANCE;

    public Connection getConnection()
    {
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/mtcgdb",
                    "mtcgdb",
                    "mtcgdb");
        } catch (SQLException e) {
            throw new DataAccessException("Datenbankverbindungsaufbau nicht erfolgreich", e);
        }
    }
}
