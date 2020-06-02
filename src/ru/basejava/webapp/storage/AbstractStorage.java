package ru.basejava.webapp.storage;

import ru.basejava.webapp.exception.ExistStorageException;
import ru.basejava.webapp.exception.NotExistStorageException;
import ru.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        int num = getIndexResume(resume.toString());
        if (num < 0) {
            throw new NotExistStorageException(resume.toString());
        } else {
            doUpdate(num, resume);
        }
    }

    public void save(Resume resume) {
        int num = getIndexResume(resume.toString());
        if (num >= 0) {
            throw new ExistStorageException(resume.toString());
        } else {
            doSave(resume, num);
        }
    }

    public Resume get(String uuid) {
        int num = getIndexResume(uuid);
        if (num < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            return doGet(num);
        }
    }

    public void delete(String uuid) {
        int num = getIndexResume(uuid);
        if (num < 0 ) {
            throw new NotExistStorageException(uuid);
        } else {
            doDelete(num);
        }
    }

    protected abstract int getIndexResume(String uuid);

    protected abstract void doUpdate(int index, Resume resume);

    protected abstract void doSave(Resume resume, int index);

    protected abstract Resume doGet(int index);

    protected abstract void doDelete(int index);
}
