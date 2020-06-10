package ru.basejava.webapp.storage;

import ru.basejava.webapp.exception.ExistStorageException;
import ru.basejava.webapp.exception.NotExistStorageException;
import ru.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        Object key = getExistedKey(resume.toString());
        doUpdate(key, resume);
    }

    public void save(Resume resume) {
        Object key = getNotExistedKey(resume.toString());
        doSave(resume, key);
    }

    public Resume get(String uuid) {
        Object key = getExistedKey(uuid);
        return doGet(key);
    }

    public void delete(String uuid) {
        Object key = getExistedKey(uuid);
        doDelete(key);
    }

    private Object getExistedKey(String uuid) {
        Object key = getKeyResume(uuid);
        if (checkKey(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private Object getNotExistedKey(String uuid) {
        Object key = getKeyResume(uuid);
        if (!checkKey(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    protected abstract Object getKeyResume(String uuid);

    protected abstract void doUpdate(Object key, Resume resume);

    protected abstract void doSave(Resume resume, Object key);

    protected abstract Resume doGet(Object key);

    protected abstract void doDelete(Object key);

    protected abstract boolean checkKey(Object key);
}
