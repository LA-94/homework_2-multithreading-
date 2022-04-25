package ru.digitalhabbits.homework2.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.digitalhabbits.homework2.FileReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileReaderImplTest {

    FileReader fileReader;
    File file;
    File emptyFile;
    File folder;
    List<String> expectedLines;

    @BeforeAll
    void initForAllTests() {
        expectedLines = List.of(
                "hello",
                "how are you",
                "Java",
                "",
                "H"
        );

        emptyFile = Files.newTemporaryFile();

        folder = Files.newTemporaryFolder();
        folder.deleteOnExit();

        file = Files.newTemporaryFile();
        file.deleteOnExit();

        writeTestValuesInFile(expectedLines);
    }

    private void writeTestValuesInFile(Collection<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    @BeforeEach
    void prepare() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void should_read_lines_as_expected_if_file_exists_and_file_is_not_empty() {
        Stream<String> actualLines = fileReader.readLines(file);

        assertThat(actualLines)
                .containsExactlyElementsOf(expectedLines);
    }

    @Test
    void should_read_lines_as_expected_if_file_exists_and_file_is_empty() {
        Stream<String> actualLines = fileReader.readLines(emptyFile);

        assertThat(actualLines)
                .containsExactlyElementsOf(List.of());
    }

    @Test
    void should_throw_null_pointer_exception_if_file_is_null() {
        assertThrows(NullPointerException.class, () -> fileReader.readLines(null));
    }

    @Test
    void should_throw_illegal_argument_exception_if_file_is_folder() {
        assertThrows(IllegalArgumentException.class, () -> fileReader.readLines(folder));
    }

    @Test
    void should_throw_illegal_argument_exception_if_file_does_not_exists() {
        File nonexistentFile = new File("nonexistentFile.txt");
        assertThrows(IllegalArgumentException.class, () -> fileReader.readLines(nonexistentFile));
    }
}
