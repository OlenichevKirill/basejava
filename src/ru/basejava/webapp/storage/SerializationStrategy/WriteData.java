package ru.basejava.webapp.storage.SerializationStrategy;

import java.io.IOException;

public interface WriteData <T> {
    void write(T t) throws IOException;
}