package org.example.report;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import org.example.model.Holiday;

import java.io.File;
import java.util.List;

public interface ReportJRXML {
    void fillReport(File jrxmlTemplate, JRDataSource dataSource, File fileToBeImported) throws JRException;
    JRDataSource createDataSourceFromJRResultSet() throws Exception;
    JRDataSource createDataSourceFromJRBean(List<Holiday> list) throws Exception;
    JRDataSource createDataSourceFromJRMap(List<Holiday> list) throws Exception;
}
