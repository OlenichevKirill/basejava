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
        int num = getNumResume(resume.toString());
        if (num < 0) {
            throw new NotExistStorageException(resume.toString());
        } else {
            doUpdate(num, resume);
        }
    }

    public void save(Resume resume) {
        int num = getNumResume(resume.toString());
        if (num >= 0) {
            throw new NotExistStorageException(resume.toString());
        } else {
            doSave(resume, num);
        }
    }

    public Resume get(String uuid) {
        int num = getNumResume(uuid);
        if (num < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            return doGet(num);
        }
    }

    public void delete(String uuid) {
        int num = getNumResume(uuid);
        if (num < 0 ) {
            throw new NotExistStorageException(uuid);
        } else {
            doDelete(num);
        }
    }

    protected abstract void doClear();

    protected abstract int getSize();

    protected abstract Resume[] doGetAll();

    protected abstract int getNumResume(String uuid);

    protected abstract void doUpdate(int index, Resume resume);

    protected abstract void doSave(Resume resume, int index);

    protected abstract Resume doGet(int index);

    protected abstract void doDelete(int index);
}
