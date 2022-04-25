package ru.digitalhabbits.homework2.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.digitalhabbits.homework2.LetterCounter;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.assertThrows;

@FieldDefaults(level = AccessLevel.PRIVATE)
class LetterCounterImplTest {

    LetterCounter letterCounter;

    @BeforeEach
    void prepare() {
        initLetterCounter();
    }

    private void initLetterCounter() {
        letterCounter = new LetterCounterImpl();
    }

    @Test
    void should_counts_characters_as_expected_if_line_is_not_empty() {
        Map<Character, Long> count = letterCounter.count("helloWorld");

        assertThat(count)
                .containsOnly(
                        entry('h', 1L),
                        entry('e', 1L),
                        entry('e', 1L),
                        entry('l', 3L),
                        entry('o', 2L),
                        entry('W', 1L),
                        entry('r', 1L),
                        entry('d', 1L)
                );
    }

    @Test
    void should_not_counts_characters_if_line_is_empty() {
        Map<Character, Long> count = letterCounter.count("");

        assertThat(count)
                .isEmpty();
    }

    @Test
    void should_throw_null_pointer_exception_if_line_is_null() {
        assertThrows(NullPointerException.class, () -> letterCounter.count(null));
    }
}
