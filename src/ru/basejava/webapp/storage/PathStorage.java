package ru.basejava.webapp.storage;

import ru.basejava.webapp.exception.StorageException;
import ru.basejava.webapp.model.Resume;
import ru.basejava.webapp.storage.SerializationStrategy.SerializationStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private SerializationStrategy strategy;

    protected PathStorage(String dir, SerializationStrategy strategy) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.strategy = strategy;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            strategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path ", resume.getUuid(), e);
        }
        doUpdate(path, resume);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return strategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.getFileName().toString());
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected List<Resume> getAll() {
        Path[] pathMas = getListPath(getStreamPath(directory)).toArray(new Path[0]);
        List<Resume> list = new ArrayList<>();
        for (int i = 0; i < pathMas.length; i++) {
            list.add(doGet(pathMas[i]));
        }
        return list;
    }

    @Override
    public void clear() {
        getStreamPath(directory).forEach(this::doDelete);
    }

    @Override
    public int size() {
        return getListPath(getStreamPath(directory)).size();
    }

    public Stream<Path> getStreamPath(Path directory) {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Path stream error", null);
        }
    }

    public List<Path> getListPath(Stream<Path> stream) {
        return stream.collect(Collectors.toList());
    }
}
