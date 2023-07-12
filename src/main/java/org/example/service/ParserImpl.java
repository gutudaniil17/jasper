package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.constants.FilePaths;
import org.example.model.Holiday;
import org.example.model.Year;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ParserImpl implements Parser {
    private SAXParserFactory factory;
    private XMLHandler handler;
    private ObjectMapper mapper;
    @Override
    public List<Holiday> parseFromXML(){
        File file = new File(FilePaths.XML_DATSOURCE_FILE);
        factory = SAXParserFactory.newInstance();
        handler = new XMLHandler();
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
            parser.parse(file, handler);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Year year = handler.getYear();
        return year.getHolidays();
    }

    @Override
    public void parseToJson(List<Holiday> list, File file){
        mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Holiday> readFromJson(File file) throws IOException {
        mapper = new ObjectMapper();
        List<Holiday> list = mapper.readValue(file, new TypeReference<>() {
        });
        return list;
    }


}
