package ru.basejava.webapp.storage;

import ru.basejava.webapp.exception.StorageException;
import ru.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume doGet(Integer index) {
        return storage[index];
    }

    public void doUpdate(Integer index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected List<Resume> getListResume() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    public int size() {
        return size;
    }

    public void doDelete(Integer key) {
        deleteElementStorage(key);
        storage[size - 1] = null;
        size--;
    }

    public void doSave(Resume resume, Integer key) {
        if (storage.length > size) {
            saveElementStorage(resume, key);
            size++;
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    @Override
    protected boolean checkKey(Integer index) {
        return index < 0;
    }

    protected abstract Integer getKeyResume(String uuid);

    protected abstract void deleteElementStorage(int position);

    protected abstract void saveElementStorage(Resume resume, int position);
}
