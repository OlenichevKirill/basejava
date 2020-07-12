package ru.basejava.webapp.storage.SerializationStrategy;

import ru.basejava.webapp.exception.StorageException;
import ru.basejava.webapp.model.Resume;
import ru.basejava.webapp.storage.SerializationStrategy.SerializationStrategy;

import java.io.*;

public class ObjectStreamSerializer implements SerializationStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
