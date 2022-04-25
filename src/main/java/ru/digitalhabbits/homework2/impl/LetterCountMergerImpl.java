package ru.digitalhabbits.homework2.impl;

import lombok.NonNull;
import ru.digitalhabbits.homework2.LetterCountMerger;

import java.util.Map;

public class LetterCountMergerImpl implements LetterCountMerger {

    @Override
    public Map<Character, Long> merge(@NonNull Map<Character, Long> first,
                                      @NonNull Map<Character, Long> second) {

        second.forEach((key, value) -> first.merge(key, value, Long::sum));
        return first;
    }
}
