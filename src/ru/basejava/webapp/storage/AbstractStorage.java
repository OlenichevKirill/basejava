package ru.basejava.webapp.storage;

import ru.basejava.webapp.exception.NotExistStorageException;
import ru.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void clear() {
        doClear();
    }

    public int size() {
        return getSize();
    }

    public Resume[] getAll() {
        return doGetAll();
    }

    public void update(Resume resume) {
        int num = getNumStorage(resume.toString());
        if (num == -1) {
            throw new NotExistStorageException(resume.toString());
        } else {
            doUpdate(num, resume);
        }
    }

    public void save(Resume resume) {
        int num = getNumStorage(resume.toString());
        if (num != -1) {
            throw new NotExistStorageException(resume.toString());
        } else {
            doSave(resume);
        }
    }

    public Resume get(String uuid) {
        int num = getNumStorage(uuid);
        if (num == -1) {
            throw new NotExistStorageException(uuid);
        } else {
            return doGet(num);
        }
    }

    public void delete(String uuid) {
        int num = getNumStorage(uuid);
        if (num == -1) {
            throw new NotExistStorageException(uuid);
        } else {
            doDelete(num);
        }
    }

    protected abstract void doClear();

    protected abstract int getSize();

    protected abstract Resume[] doGetAll();

    protected abstract int getNumStorage(String uuid);

    protected abstract void doUpdate(int index, Resume resume);

    protected abstract void doSave(Resume resume);

    protected abstract Resume doGet(int index);

    protected abstract void doDelete(int index);
}
