package com.property.filegenerator;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertyFileGenerator {
    public static void main(String[] args) throws IOException {
        File file = createFileObject("src/test.properties");
        File file1 = createFileObject("src/test2.txt");
        BufferedReader bufferedReader = createAReaderObject(file);
        BufferedReader bufferedReader1 = createAReaderObject(file1);
        Map<String, String> with_key_values = convertTomap(bufferedReader.lines(), "=");
        Map<String, String> with_only_values = convertTomap(bufferedReader1.lines(), ",");
        bufferedReader.close();
        bufferedReader1.close();
        List<String> keyValueParis = new ArrayList<>();
        List<String> notFoundList = new ArrayList<>();
        List<List<String>> lists = findAndAppendkeyWithValue(with_key_values, with_only_values);
        keyValueParis = lists.get(0);
        GenerateFile generateFile = new GenerateFile();
        generateFile.createFileFromList(keyValueParis);
        System.out.println(notFoundList);
    }

    public static Map<String, String> convertTomap(Stream<String> stream, String delimiter) {
        return stream.filter(current_line -> current_line.length() > 0)
                .map(cl -> cl.split(delimiter))
                .collect(Collectors.toMap(cl -> cl[0], cl -> cl[1]));
    }

    public static BufferedReader createAReaderObject(File file) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }

    public static File createFileObject(String pathOrFileName) {
        return new File(pathOrFileName);
    }

    /*
    @param with_key_and_values contains key & value in english
    @param with_values contains values like english,languagespecific
     */
    public static List<List<String>> findAndAppendkeyWithValue(Map<String, String> with_key_and_values, Map<String, String> with_values) {
//        using streams
        List<List<String>> values = with_values.entrySet().stream().map(valueSet -> with_key_and_values.entrySet().stream().filter(keyValue -> valueSet.getKey().equals(keyValue.getValue()))
                .map(keyValue -> keyValue.getKey() + "=" + valueSet.getValue())
                .collect(Collectors.toList()))
                .collect(Collectors.toList());
        return values;
    }
}
