package ru.basejava.webapp.storage;

import ru.basejava.webapp.exception.ExistStorageException;
import ru.basejava.webapp.exception.NotExistStorageException;
import ru.basejava.webapp.model.Resume;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());


    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SK key = getExistedKey(resume.getUuid());
        doUpdate(key, resume);
    }

    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK key = getNotExistedKey(resume.getUuid());
        doSave(resume, key);
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK key = getExistedKey(uuid);
        return doGet(key);
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK key = getExistedKey(uuid);
        doDelete(key);
    }

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> listResume = getAll();
        Collections.sort(listResume, RESUME_COMPARATOR);
        return listResume;
    }

    private SK getExistedKey(String uuid) {
        SK key = getSearchKey(uuid);
        if (!isExist(key)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private SK getNotExistedKey(String uuid) {
        SK key = getSearchKey(uuid);
        if (isExist(key)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    protected abstract SK getSearchKey(String uuid);

    protected abstract void doUpdate(SK key, Resume resume);

    protected abstract void doSave(Resume resume, SK key);

    protected abstract Resume doGet(SK key);

    protected abstract void doDelete(SK key);

    protected abstract boolean isExist(SK key);

    protected abstract List<Resume> getAll();
}
