package ru.basejava.webapp.storage.SerializationStrategy;

import java.io.IOException;

public interface ReadData<T> {
    T read() throws IOException;
}
