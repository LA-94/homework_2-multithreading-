package ru.digitalhabbits.homework2.impl;

import lombok.NonNull;
import ru.digitalhabbits.homework2.FileLetterCounter;
import ru.digitalhabbits.homework2.FileLetterCounterFactory;

import java.util.concurrent.Executors;

public class FileLetterCounterFactoryImpl implements FileLetterCounterFactory {
    @Override
    public FileLetterCounter create() {
        return createBy(Type.ASYNC);
    }

    @Override
    public FileLetterCounter createBy(@NonNull Type type) {
        switch (type) {
            case ASYNC:
                return createAsyncFileLetterCounter();
            default:
                throw new UnsupportedOperationException(type + " doesn't support");
        }
    }

    private FileLetterCounter createAsyncFileLetterCounter() {
        return new AsyncFileLetterCounter(
                new FileReaderImpl(),
                new LetterCounterImpl(),
                new LetterCountMergerImpl(),
                Executors.newWorkStealingPool()
        );
    }
}
