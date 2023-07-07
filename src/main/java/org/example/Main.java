package org.example;


import org.example.model.Holiday;
import org.example.model.Year;
import org.example.service.XMLHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        File file = new File("C:\\Users\\crme039\\gutuprojects\\jasper\\src\\main\\resources\\MyDataBase.xml");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler handler = new XMLHandler();
        parser.parse(file,handler);
        Year year =handler.getYear();
        for(Holiday holiday: year.getHolidays()){
            System.out.println(holiday.getName());
        }
    }
}
