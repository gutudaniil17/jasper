package org.example.dao;

import org.example.model.Holiday;

import java.sql.*;
import java.util.List;

public class HolidayDAO {
    private static Connection connection;

    public static void connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String username = "root";
        String password = "root";
        connection = DriverManager.getConnection(url, username, password);
    }

    public static void addHolidays(List<Holiday> holidays) throws SQLException {
        String sql = "INSERT INTO holidays (country, date, name) VALUES (?, ?, ?)";
        connect();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            for (Holiday holiday : holidays) {
                statement.setString(1, holiday.getCountry());
                statement.setString(2, holiday.getDate());
                statement.setString(3, holiday.getName());
                statement.executeUpdate();
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public static ResultSet getHolidaysFromTable() throws SQLException {
        String sql = "select * from holidays";
        connect();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

}

