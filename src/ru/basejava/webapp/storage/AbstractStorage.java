package ru.basejava.webapp.storage;

import ru.basejava.webapp.exception.ExistStorageException;
import ru.basejava.webapp.exception.NotExistStorageException;
import ru.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        Object index = getIndexResume(resume.toString());
        if (checkIndex(index)) {
            throw new NotExistStorageException(resume.toString());
        } else {
            doUpdate(index, resume);
        }
    }

    public void save(Resume resume) {
        Object index = getIndexResume(resume.toString());
        if (!checkIndex(index)) {
            throw new ExistStorageException(resume.toString());
        } else {
            doSave(resume, index);
        }
    }

    public Resume get(String uuid) {
        Object index = getIndexResume(uuid);
        if (checkIndex(index)) {
            throw new NotExistStorageException(uuid);
        } else {
            return doGet(index);
        }
    }

    public void delete(String uuid) {
        Object index = getIndexResume(uuid);
        if (checkIndex(index)) {
            throw new NotExistStorageException(uuid);
        } else {
            doDelete(index);
        }
    }

    protected abstract Object getIndexResume(String uuid);

    protected abstract void doUpdate(Object index, Resume resume);

    protected abstract void doSave(Resume resume, Object index);

    protected abstract Resume doGet(Object index);

    protected abstract void doDelete(Object index);

    protected abstract boolean checkIndex(Object index);
}
