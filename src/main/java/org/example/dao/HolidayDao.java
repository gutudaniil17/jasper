package org.example.dao;

import org.example.model.Holiday;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface HolidayDao {
    void connect() throws SQLException;
    void addHolidays(List<Holiday> holidays) throws SQLException;
    ResultSet getHolidaysFromTable() throws SQLException;
}
