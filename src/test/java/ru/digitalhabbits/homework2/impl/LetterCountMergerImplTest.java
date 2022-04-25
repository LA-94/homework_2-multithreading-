package ru.digitalhabbits.homework2.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.digitalhabbits.homework2.LetterCountMerger;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class LetterCountMergerImplTest {

    LetterCountMerger merger;

    HashMap<Character, Long> firstCount;
    HashMap<Character, Long> secondCount;

    @BeforeEach
    void prepare() {
        initMerger();
        initFirstCount();
        initSecondCount();
    }

    private void initMerger() {
        merger = new LetterCountMergerImpl();
    }

    private void initFirstCount() {
        firstCount = new HashMap<>();
        firstCount.put('a', 3L);
        firstCount.put('b', 2L);
        firstCount.put('c', 1L);
        firstCount.put('d', 4L);
    }

    private void initSecondCount() {
        secondCount = new HashMap<>();
        secondCount.put('c', 2L);
        secondCount.put('d', 3L);
        secondCount.put('z', 2L);
        secondCount.put('t', 3L);
    }

    @Test
    void should_merged_counts_if_first_counts_is_not_empty_and_second_counts_is_not_empty() {
        Map<Character, Long> mergedCounts = merger.merge(firstCount, secondCount);
        assertThat(mergedCounts)
                .containsOnly(
                        entry('a', 3L),
                        entry('b', 2L),
                        entry('c', 3L),
                        entry('d', 7L),
                        entry('z', 2L),
                        entry('t', 3L)
                );
    }

    @Test
    void should_merged_counts_if_first_counts_is_empty_and_second_counts_is_not_empty() {
        Map<Character, Long> mergedCounts = merger.merge(new HashMap<>(), secondCount);
        assertThat(mergedCounts)
                .containsOnly(
                        entry('c', 2L),
                        entry('d', 3L),
                        entry('z', 2L),
                        entry('t', 3L)
                );
    }

    @Test
    void should_merged_counts_if_first_counts_is_not_empty_and_second_counts_is_empty() {
        Map<Character, Long> mergedCounts = merger.merge(firstCount, new HashMap<>());
        assertThat(mergedCounts)
                .containsOnly(
                        entry('a', 3L),
                        entry('b', 2L),
                        entry('c', 1L),
                        entry('d', 4L)
                );
    }

    @Test
    void first_counts_should_be_equal_to_merged_counts() {
        Map<Character, Long> mergedCounts = merger.merge(firstCount, secondCount);
        assertEquals(mergedCounts, firstCount);
    }

    @Test
    void should_throw_null_pointer_exception_if_first_count_is_null_and_second_count_is_not_empty() {
        assertThrows(NullPointerException.class, () -> merger.merge(null, secondCount));
    }

    @Test
    void should_throw_null_pointer_exception_if_first_count_is_not_empty_and_second_count_is_null() {
        assertThrows(NullPointerException.class, () -> merger.merge(firstCount, null));
    }
}
