package com.property.filegenerator;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenerateFile {
    Logger logger = Logger.getLogger(GenerateFile.class.toString());

    /**
     * This method takes a list of localized key value pair list
     * and add the list to a file in UTF_8 format.
     *
     * @param keyValuePairsList
     * @throws IOException
     */
    public void createFileFromList(List<String> keyValuePairsList, String fileName) throws IOException {
        if (!keyValuePairsList.isEmpty() && keyValuePairsList != null) {
            Path filePath = Paths.get("src".concat(File.separator).concat(fileName));
            Files.write(filePath, keyValuePairsList, StandardCharsets.UTF_8);
            logger.info("file generated with name : " + filePath.getFileName());
        }
    }

    /**
     * This method extracts the values from properties file
     * @param keyValuePairs
     * @return
     */
    public Set<String> extractvalsFromPropertiesFile(Stream<String> keyValuePairs) {
        logger.info("Extracting values from properties file");
        return keyValuePairs.filter(str -> str.length() > 0)
                .map(str -> str.split("="))
                .filter(cl -> cl.length >= 2)
                .map(cl -> cl[1].trim())
                .collect(Collectors.toSet());
    }
}
