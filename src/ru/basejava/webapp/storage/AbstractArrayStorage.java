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

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume get(String uuid) {
        Resume res = null;
        int num = getNumResume(uuid);
        if (num >= 0) {
            res = storage[num];
        } else {
            throw new NotExistStorageException(uuid);
        }
        return res;
    }

    public void update(Resume resume) {
        int num = getNumResume(resume.toString());
        if (num >= 0) {
            storage[num] = resume;
        } else {
            throw new NotExistStorageException(resume.toString());
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public void delete(String uuid) {
        int num = getNumResume(uuid);
        if (num >= 0) {
            deleteElementStorage(num);
            storage[size - 1] = null;
            size--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void save(Resume resume) {
        int num = getNumResume(resume.toString());
        if (storage.length > size) {
            if (num < 0) {
                saveElementStorage(resume, num);
                size++;
            } else {
                throw new ExistStorageException(resume.toString());
            }
        } else {
            throw new StorageException("Storage overflow", resume.toString());
        }
    }

    protected abstract int getNumResume(String uuid);

    protected abstract void deleteElementStorage(int position);

    protected abstract void saveElementStorage(Resume resume, int position);
}
