package org.example.service;

import org.example.model.Holiday;
import org.example.model.Year;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class XMLHandler extends DefaultHandler {
    private Year year;
    private List<Holiday> holidays;
    private Holiday currentHoliday;
    private String currentElement;

    public Year getYear() {
        return year;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = qName;
        if (qName.equalsIgnoreCase("Year-2021")) {
            year = new Year();
            holidays = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("holydays")) {
            currentHoliday = new Holiday();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();
        if (value.isEmpty()) {
            return;
        }

        if (currentElement.equalsIgnoreCase("COUNTRY")) {
            currentHoliday.setCountry(value);
        } else if (currentElement.equalsIgnoreCase("DATE")) {
            currentHoliday.setDate(value);
        } else if (currentElement.equalsIgnoreCase("NAME")) {
            currentHoliday.setName(value);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("holydays")) {
            holidays.add(currentHoliday);
            currentHoliday = null;
        } else if (qName.equalsIgnoreCase("Year-2021")) {
            year.setHolidays(holidays);
        }
    }
}
