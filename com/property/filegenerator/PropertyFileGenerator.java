package com.property.filegenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertyFileGenerator {
    private final static Logger logger = Logger.getLogger(PropertyFileGenerator.class.toString());
    public static void main(String[] args) throws IOException {
        File file = createFileObject("src/test.properties");
        File file1 = createFileObject("src/test2.txt");
        BufferedReader bufferedReader = createAReaderObject(file);
        BufferedReader bufferedReader1 = createAReaderObject(file1);
        Map<String, String> with_key_values = convertTomap(bufferedReader.lines(), "=");
        System.out.println(with_key_values);
        Map<String, String> with_only_values = convertTomap(bufferedReader1.lines(), ",");
        System.out.println(with_only_values);
        bufferedReader.close();
        bufferedReader1.close();
        List<String> keyValueParis = new ArrayList<>();
        List<String> notFoundList = new ArrayList<>();
        List<String> lists = findAndAppendkeyWithValue(with_key_values, with_only_values);
        keyValueParis = lists;
        GenerateFile generateFile = new GenerateFile();
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
     * @return File
     */
    public static File createFileObject(String pathOrFileName) {
        logger.info("creating a file object with path or file name : " + pathOrFileName);
        return new File(pathOrFileName);
    }

    /**
     * creates buffered reader object with file object
     * @param file
     * @return BufferedReader
     * @throws FileNotFoundException
     */
    public static BufferedReader createAReaderObject(File file) throws FileNotFoundException {
        logger.info("creating a reader object for file : " + file.getName());
        return new BufferedReader(new InputStreamReader(new FileInputStream(file)));
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
        List<String> values = with_values.entrySet().stream().map(valueSet -> with_key_and_values.entrySet().stream().filter(keyValue -> keyValue.getValue().trim().equalsIgnoreCase(valueSet.getKey().trim()))
                .map(keyValue -> keyValue.getKey().trim() + "=" + valueSet.getValue().trim())
                .collect(Collectors.toList()))
                .flatMap(list -> list.stream()).collect(Collectors.toList());
        return values;
    }
}