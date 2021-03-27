package com.property.filegenerator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenerateFile {
    Logger logger = Logger.getLogger(GenerateFile.class.toString());
    public void createFileFromList(List<String> keyValuePairsList) throws IOException {
        Path filePath = Paths.get("src/com/property/filegenerator/file.properties");
        Files.write(filePath,keyValuePairsList, StandardCharsets.UTF_8);
        logger.info("file generated with name : " + filePath.getFileName());
    }

    /**
     * Removes whitespaces and new lines in the filestream
     * @param fileStream
     * @return
     */
    public Stream<String> removeSpacesAndNewlines(Stream<String> fileStream){
        logger.info("formatting ");
        List<String> formattedStream = fileStream.filter(line -> line.length() > 0)
                .map(line -> line.replaceAll("\\s+",""))
                .map(line -> line.trim())
                .collect(Collectors.toList());
        return formattedStream.stream();
    }
}
