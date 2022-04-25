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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.util.stream.Collector.of;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class AsyncFileLetterCounter implements FileLetterCounter {
    FileReader reader;
    LetterCounter letterCounter;
    LetterCountMerger merger;

    ExecutorService executorService;

    @Override
    public Map<Character, Long> count(@NonNull File input) {
        List<Future<Map<Character, Long>>> numberOfLetters = reader.readLines(input)
                .map(this::countInThread)
                .collect(toList());

        var collector = of(HashMap::new,
                merger::merge,
                merger::merge);

        return numberOfLetters.stream()
                .map(this::get)
                .collect(collector);
    }

    private Future<Map<Character, Long>> countInThread(String line) {
        return executorService.submit(() -> letterCounter.count(line));
    }

    @SneakyThrows
    private Map<Character, Long> get(Future<Map<Character, Long>> numberOfLetters) {
        return numberOfLetters.get();
    }
}
