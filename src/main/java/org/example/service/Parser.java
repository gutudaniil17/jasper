package org.example.service;

import org.example.model.Holiday;
import org.example.model.Year;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.List;

public class Parser {
    public static List<Holiday> parse() throws Exception{
        File file = new File("C:\\Users\\crme039\\gutuprojects\\jasper\\src\\main\\resources\\MyDataBase.xml");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler handler = new XMLHandler();
        parser.parse(file,handler);
        Year year =handler.getYear();
        return year.getHolidays();
    }
}
