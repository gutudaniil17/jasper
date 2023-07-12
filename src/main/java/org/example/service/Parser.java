package org.example.service;

import org.example.model.Holiday;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Parser {
    List<Holiday> parseFromXML() throws Exception;
    void parseToJson(List<Holiday> list,File file) throws IOException;
    List<Holiday> readFromJson(File file) throws IOException;
}
