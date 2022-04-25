package ru.digitalhabbits.homework2;

import lombok.NonNull;

public interface FileLetterCounterFactory extends Factory<FileLetterCounter> {

    FileLetterCounter createBy(@NonNull Type type);

    enum Type {
        ASYNC
    }
}
