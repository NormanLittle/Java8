package com.sandbox;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.net.URLConnection.guessContentTypeFromStream;

public class ImageTypeInspector {

    private static List<File> getImageFiles() {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        return Stream.of("image.jpg", "image.png", "image.gif")
                     .map(classLoader::getResource)
                     .map(URL::getFile)
                     .map(File::new)
                     .collect(Collectors.toList());
    }

    private static String readImageTypeFrom(byte[] bytes) throws IOException {
        return guessContentTypeFromStream(new ByteArrayInputStream(bytes));
    }

    public static void main(String[] args) throws IOException {
        for (Path path : getImageFiles().stream()
                                        .map(File::toPath)
                                        .collect(Collectors.toList())) {
            byte[] bytes = Files.readAllBytes(path);
            System.out.printf("File: %s; Type: %s%n", path.getFileName(), readImageTypeFrom(bytes));
        }
    }
}
