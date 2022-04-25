package ru.digitalhabbits.homework2;

import lombok.NonNull;

import java.util.Map;

/**
 * Counter characters in given string
 */
public interface LetterCounter {

    Map<Character, Long> count(@NonNull String input);

}
