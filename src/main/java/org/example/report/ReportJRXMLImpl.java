package org.example.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.example.dao.HolidayDAOImpl;
import org.example.dao.HolidayDao;
import org.example.model.Holiday;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportJRXMLImpl implements ReportJRXML {
    private JRDataSource dataSource;
    HolidayDao holidayDao;

    @Override
    public void fillReport(File jrxmlTemplate, JRDataSource dataSource, File fileToBeImported) throws JRException {
        JasperReport report = JasperCompileManager.compileReport(jrxmlTemplate.getAbsolutePath());
        JasperPrint print = JasperFillManager.fillReport(report, null, dataSource);
        JasperExportManager.exportReportToPdfFile(print, fileToBeImported.getAbsolutePath());
    }

    @Override
    public JRDataSource createDataSourceFromJRResultSet() throws Exception {
        holidayDao = new HolidayDAOImpl();
        ResultSet resultSet = holidayDao.getHolidaysFromTable();
        dataSource = new JRResultSetDataSource(resultSet);
        return dataSource;
    }

    @Override
    public JRDataSource createDataSourceFromJRBean(List<Holiday> list) {
        dataSource = new JRBeanCollectionDataSource(list);
        return dataSource;
    }

    @Override
    public JRDataSource createDataSourceFromJRMap(List<Holiday> list) {
        List<Map<String, ?>> maps = new ArrayList<>();
        for (Holiday holiday : list) {
            Map<String, String> map = new HashMap<>();
            map.put("country", holiday.getCountry());
            map.put("date", holiday.getDate());
            map.put("name", holiday.getName());
            maps.add(map);
        }
        dataSource = new JRMapCollectionDataSource(maps);
        return dataSource;
    }
}
