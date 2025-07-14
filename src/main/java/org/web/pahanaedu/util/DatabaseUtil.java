package org.web.pahanaedu.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil
{
    private static final String JDBC_URL = "jdbc:h2:~/pahanaedu"; // Or use jdbc:h2:mem:pahanaedu for in-memory
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    static
    {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }
}