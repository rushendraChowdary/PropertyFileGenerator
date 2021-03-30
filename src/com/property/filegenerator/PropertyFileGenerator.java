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
    private static List<String> notFoundList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Stream<String> fileStream1 = createStreamFromPath("src"+File.separator+"test.properties");
        Stream<String> fileStream2 = createStreamFromPath("src"+File.separator+"test.txt");
            Map<String, String> with_key_values = convertTomap(fileStream1, "=");
            Map<String, String> with_only_values = convertTomap(fileStream2, "\\|");
            if (!with_key_values.isEmpty() && with_key_values != null && !with_only_values.isEmpty() && with_only_values != null) {
                GenerateFile generateFile = new GenerateFile();
                generateFile.createFileFromList(findAndAppendkeyWithValue(with_key_values, with_only_values));
            } else {
                logger.info("Either one of your files are not following the rules");
            }
    }

    /**
     * creating file object with path
     *
     * @param pathOrFileName
     * @return Stream<String>
     */
    public static Stream<String> createStreamFromPath(String pathOrFileName) throws IOException {
        logger.info("creating a file object with path or file name : " + pathOrFileName);
        return Files.lines(Paths.get(pathOrFileName));
    }

    /**
     * Split stream<String> with delimiter as key value pairs & add it to the map
     *
     * @param stream
     * @param delimiter
     * @return Map<String, String>
     */
    public static Map<String, String> convertTomap(Stream<String> stream, String delimiter) {
        logger.info("converting stream of strings to Map using delimiter : " + delimiter);
        return stream.filter(current_line -> current_line.length() > 0)
                .map(cl -> cl.split(delimiter))
                .filter(cl -> cl.length > 1)
                .collect(Collectors.toMap(cl -> cl[0].trim(), cl -> cl[1].trim(), (val1, val2) -> val2.trim()));
    }

    /**
     * Takes two maps one with key value pairs(for example key=value) and
     * the other with only values (for example english,french localized values)
     *
     * @param with_key_and_values (key=value format)
     * @param with_values         (key,value or english,french)
     * @return List<String>
     */
    public static List<String> findAndAppendkeyWithValue(Map<String, String> with_key_and_values, Map<String, String> with_values) {
        logger.info("Streaming two different maps to find and append key with value");
        return with_values.entrySet().stream().filter(valueSet -> isContainskeyInMap(valueSet.getKey(), with_key_and_values))
                .map(valueSet -> compareValueWithKeyValMap(valueSet,with_key_and_values))
                .flatMap(list -> list.stream()).collect(Collectors.toList());
    }

    /**
     * Checks if a key is present in the Map of key value pairs and retuns
     * true if exists otherwise add the key to not found list and retuns false
     *
     * @param key
     * @param keyValuePair
     * @return boolean
     */
    public static boolean isContainskeyInMap(String key, Map<String, String> keyValuePair) {
        if (keyValuePair.values().contains(key)) {
            return true;
        } else {
            notFoundList.add(key);
            return false;
        }
    }

    /**
     * Compare each entry key with map of key value pairs & collect it to list
     * @param valueSet
     * @param with_key_values
     * @return
     */
    public static List<String> compareValueWithKeyValMap(Map.Entry<String,String> valueSet, Map<String,String> with_key_values){
        return with_key_values.entrySet().stream().filter(keyVal -> keyVal.getValue().equalsIgnoreCase(valueSet.getKey()))
                .map(keyVal -> keyVal.getKey().concat("=").concat(valueSet.getValue()))
                .collect(Collectors.toList());
    }
}