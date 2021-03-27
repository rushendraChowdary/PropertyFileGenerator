package com.property.filegenerator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertyFileGenerator {
    private final static Logger logger = Logger.getLogger(PropertyFileGenerator.class.toString());
    public static void main(String[] args) throws IOException {
        Stream<String> fileStream1 = createStreamFromPath("src/test.properties");
        Stream<String> fileStream2 = createStreamFromPath("src/test2.txt");
        GenerateFile generateFile = new GenerateFile();
        Stream<String> formattedFileStream1 = generateFile.removeSpacesAndNewlines(fileStream1);
        Stream<String> formattedFileStream2 = generateFile.removeSpacesAndNewlines(fileStream2);
        Map<String, String> with_key_values = convertTomap(formattedFileStream1, "=");
        Map<String, String> with_only_values = convertTomap(formattedFileStream2, "\\|");
        List<String> notFoundList = new ArrayList<>();
        List<String> keyValueParis = findAndAppendkeyWithValue(with_key_values, with_only_values);
        generateFile.createFileFromList(keyValueParis);
        System.out.println(notFoundList);
    }

    /**
     * Take stream of string and delimiter and convert that to Map object
     * @param stream
     * @param delimiter
     * @return  Map<String,String>
     */
    public static Map<String, String> convertTomap(Stream<String> stream, String delimiter) {
        logger.info("converting stream of strings to Map using delimiter : " + delimiter);
        return stream.filter(current_line -> current_line.length() > 0)
                .map(cl -> cl.split(delimiter))
                .filter(cl -> cl.length > 1)
                .collect(Collectors.toMap(cl -> cl[0], cl -> cl[1], (val1, val2) -> val2));
    }

    /**
     *  creating file object with path
     * @param pathOrFileName
     * @return Stream<String>
     */
    public static Stream<String> createStreamFromPath(String pathOrFileName) throws IOException {
        logger.info("creating a file object with path or file name : " + pathOrFileName);
        return Files.lines(Paths.get(pathOrFileName));
    }

    /**
     * Takes two maps one with key value pairs(for example key=value) and
     * the other with only values (for example english,french localized values)
     * @param with_key_and_values (key=value format)
     * @param with_values (key,value or english,french)
     * @return List<String>
     */
    public static List<String> findAndAppendkeyWithValue(Map<String, String> with_key_and_values, Map<String, String> with_values) {
        logger.info("Streaming two different maps to find and append key with value");
        return with_values.entrySet().stream().map(valueSet -> with_key_and_values.entrySet().stream().filter(keyValue -> keyValue.getValue().trim().equalsIgnoreCase(valueSet.getKey().trim()))
                .map(keyValue -> keyValue.getKey().trim() + "=" + valueSet.getValue().trim())
                .collect(Collectors.toList()))
                .flatMap(list -> list.stream()).collect(Collectors.toList());
    }
}