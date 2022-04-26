package ru.digitalhabbits.homework2.impl;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import ru.digitalhabbits.homework2.FileLetterCounter;
import ru.digitalhabbits.homework2.FileReader;
import ru.digitalhabbits.homework2.LetterCountMerger;
import ru.digitalhabbits.homework2.LetterCounter;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.toCollection;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class AsyncFileLetterCounter implements FileLetterCounter {
    FileReader reader;
    LetterCounter letterCounter;
    LetterCountMerger merger;

    ExecutorService executorService;

    @Override
    public Map<Character, Long> count(@NonNull File input) {
        Queue<Future<Map<Character, Long>>> numberOfLetters = reader.readLines(input)
                .map(this::countInThread)
                .collect(toCollection(LinkedList::new));

        if (numberOfLetters.isEmpty()) {
            return new HashMap<>();
        }

        while (numberOfLetters.size() > 1) {
            var firstCount = numberOfLetters.poll();
            var secondCount = numberOfLetters.poll();
            var mergedCount = mergeInThread(firstCount, secondCount);
            numberOfLetters.add(mergedCount);
        }

        var result = numberOfLetters.poll();
        return get(result);
    }

    private Future<Map<Character, Long>> countInThread(String line) {
        return executorService.submit(() -> letterCounter.count(line));
    }

    private Future<Map<Character, Long>> mergeInThread(Future<Map<Character, Long>> firstCount,
                                                       Future<Map<Character, Long>> secondCount) {
        return executorService.submit(() -> merger.merge(
                get(firstCount),
                get(secondCount)
        ));
    }

    @SneakyThrows
    private Map<Character, Long> get(Future<Map<Character, Long>> numberOfLetters) {
        return numberOfLetters.get();
    }
}
