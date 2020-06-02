package ru.basejava.webapp.storage;

import ru.basejava.webapp.exception.ExistStorageException;
import ru.basejava.webapp.exception.NotExistStorageException;
import ru.basejava.webapp.exception.StorageException;
import ru.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void doClear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume doGet(int index) {
        Resume res = null;
        res = storage[index];
        return res;
    }

    public void doUpdate(int index, Resume resume) {
        storage[index] = resume;
    }

    public Resume[] doGetAll() {
        return Arrays.copyOf(storage, size);
    }

    public int getSize() {
        return size;
    }

    public void doDelete(int index) {
        deleteElementStorage(index);
        storage[size - 1] = null;
        size--;
    }

    public void doSave(Resume resume, int index) {
        if (storage.length > size) {
            saveElementStorage(resume, index);
            size++;
        } else {
            throw new StorageException("Storage overflow", resume.toString());
        }
    }

    protected abstract int getNumResume(String uuid);

    protected abstract void deleteElementStorage(int position);

    protected abstract void saveElementStorage(Resume resume, int position);
}
