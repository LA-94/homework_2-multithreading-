package ru.digitalhabbits.homework2.impl;

import lombok.NonNull;
import lombok.SneakyThrows;
import ru.digitalhabbits.homework2.FileReader;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileReaderImpl implements FileReader {

    @Override
    @SneakyThrows
    public Stream<String> readLines(@NonNull File file) throws IllegalArgumentException {
        Path path = file.toPath();
        validate(path);

        return Files.lines(path);
    }

    private void validate(Path path) {
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("File doesn't exists");
        }

        if (Files.isDirectory(path)) {
            throw new IllegalArgumentException("File is folder");
        }
    }
}
