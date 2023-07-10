package org.example;

import org.example.report.ReportJRXML;

public class Main {
    public static void main(String[] args){
        /*new Report();*/
        ReportJRXML reportJRXML = new ReportJRXML();
        try {
            reportJRXML.fillWithJRMap();
            reportJRXML.fillWithJRResultSet();
            reportJRXML.fillWithJRBean();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
