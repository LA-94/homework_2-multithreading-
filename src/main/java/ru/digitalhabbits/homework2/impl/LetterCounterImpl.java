package ru.digitalhabbits.homework2.impl;

import lombok.NonNull;
import ru.digitalhabbits.homework2.LetterCounter;

import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class LetterCounterImpl implements LetterCounter {

    @Override
    public Map<Character, Long> count(@NonNull String input) {
        return input.chars()
                .mapToObj(this::convertToLetterFrom)
                .collect(toMap(Function.identity(), letter -> 1L, Long::sum));
    }

    private Character convertToLetterFrom(int letterCode) {
        return (char) letterCode;
    }
}
