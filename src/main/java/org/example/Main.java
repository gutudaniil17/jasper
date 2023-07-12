package org.example;

import org.example.model.Holiday;
import org.example.service.Parser;
import org.example.service.ParserImpl;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        /*File file1 = new File("src/main/resources/Holidays.jrxml");
        ReportJRXML reportJRXML = new ReportJRXMLImpl();
        JRDataSource dataSource;
        Parser parser = new ParserImpl();
        List<Holiday> list = parser.parseFromXML();
        try {
            dataSource = reportJRXML.createDataSourceFromJRMap(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        File file2 = new File("src/main/resources/report.pdf");
        try {
            reportJRXML.fillReport(file1,dataSource,file2);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
        Parser parser = new ParserImpl();
        File file = new File("src/main/resources/holidays.json");
        List<Holiday> list = null;
        try {
            list = parser.parseFromXML();
            parser.parseToJson(list,file);
            list = parser.readFromJson(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (Holiday holiday: list){
            System.out.println(holiday.getName());
        }

    }
}
