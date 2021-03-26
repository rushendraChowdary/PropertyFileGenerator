package com.property.filegenerator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GenerateFile {
    public void createFileFromList(List<String> keyValuePairsList) throws IOException {
        Path filePath = Paths.get("src/com/property/filegenerator/file.properties");
        Files.write(filePath,keyValuePairsList, StandardCharsets.UTF_8);
    }
}
