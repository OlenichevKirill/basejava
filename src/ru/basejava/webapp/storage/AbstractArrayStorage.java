package ru.basejava.webapp.storage;

import ru.basejava.webapp.exception.StorageException;
import ru.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume doGet(Object index) {
        return storage[(int) index];
    }

    public void doUpdate(Object index, Resume resume) {
        storage[(int) index] = resume;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public void doDelete(Object index) {
        deleteElementStorage((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    public void doSave(Resume resume, Object index) {
        if (storage.length > size) {
            saveElementStorage(resume, (Integer) index);
            size++;
        } else {
            throw new StorageException("Storage overflow", resume.toString());
        }
    }

    @Override
    protected boolean checkIndex(Object index) {
        if ((Integer) index < 0) {
            return true;
        } else {
            return false;
        }
    }

    protected abstract Integer getIndexResume(String uuid);

    protected abstract void deleteElementStorage(int position);

    protected abstract void saveElementStorage(Resume resume, int position);
}
