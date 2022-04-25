package ru.digitalhabbits.homework2;

import lombok.NonNull;

import java.io.File;
import java.util.stream.Stream;

/**
 * Sequential file reader
 */
public interface FileReader {

    /**
     * @throws IllegalStateException if the case when file did not exist or file is directory.
     */
    Stream<String> readLines(@NonNull File file) throws IllegalArgumentException;

}
