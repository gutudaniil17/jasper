package org.example.dao;

import org.example.constants.DatabaseConnectionStrings;
import org.example.model.Holiday;

import java.sql.*;
import java.util.List;

public class HolidayDAOImpl implements HolidayDao {
    private Connection connection;

    @Override
    public void connect() throws SQLException {
        String url = DatabaseConnectionStrings.URL;
        String username = DatabaseConnectionStrings.USERNAME;
        String password = DatabaseConnectionStrings.PASSWORD;
        connection = DriverManager.getConnection(url, username, password);
    }

    @Override
    public void addHolidays(List<Holiday> holidays) throws SQLException {
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

    @Override
    public ResultSet getHolidaysFromTable() throws SQLException {
        String sql = "select * from holidays";
        connect();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

}

