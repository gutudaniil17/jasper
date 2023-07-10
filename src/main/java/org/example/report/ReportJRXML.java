package org.example.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.example.dao.HolidayDAO;
import org.example.model.Holiday;
import org.example.service.Parser;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportJRXML {
    public void fillWithJRMap() throws Exception {
        JasperReport report = JasperCompileManager.compileReport("src/main/resources/Holidays.jrxml");
        JRDataSource dataSource = createDataSourceFromJRMap();
        JasperPrint print = JasperFillManager.fillReport(report,null,dataSource);
        JasperExportManager.exportReportToPdfFile(print,"src/main/resources/ReportMap.pdf");
    }
    public void fillWithJRResultSet() throws Exception{
        JasperReport report = JasperCompileManager.compileReport("src/main/resources/Holidays.jrxml");
        JRDataSource dataSource = createDataSourceFromJRResultSet();
        JasperPrint print = JasperFillManager.fillReport(report,null,dataSource);
        JasperExportManager.exportReportToPdfFile(print,"src/main/resources/ReportResultSet.pdf");
    }
    public void fillWithJRBean() throws Exception{
        JasperReport report = JasperCompileManager.compileReport("src/main/resources/Holidays.jrxml");
        JRDataSource dataSource = createDataSourceFromJRBean();
        JasperPrint print = JasperFillManager.fillReport(report,null,dataSource);
        JasperExportManager.exportReportToPdfFile(print,"src/main/resources/ReportBean.pdf");
    }

    private JRDataSource createDataSourceFromJRResultSet() throws Exception{
        ResultSet resultSet = HolidayDAO.getHolidaysFromTable();
        JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(resultSet);
        return resultSetDataSource;
    }
    private JRDataSource createDataSourceFromJRBean() throws Exception {
        List<Holiday> list = Parser.parse();
        JRBeanCollectionDataSource collectionDataSource = new JRBeanCollectionDataSource(list);
        return collectionDataSource;
    }
    private JRDataSource createDataSourceFromJRMap() throws Exception {
        List<Holiday> list = Parser.parse();
        List<Map<String,?>> maps = new ArrayList<>();
        for(Holiday holiday : list){
            Map<String,String> map = new HashMap<>();
            map.put("country",holiday.getCountry());
            map.put("date",holiday.getDate());
            map.put("name",holiday.getName());
            maps.add(map);
        }
        JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(maps);
        return dataSource;
    }
}
