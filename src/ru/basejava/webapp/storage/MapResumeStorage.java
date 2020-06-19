package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {

    Map<String, Resume> map = new HashMap<>();

    @Override
    protected Resume getKeyResume(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected void doUpdate(Resume key, Resume resume) {
        map.put(key.getUuid(), resume);
    }

    @Override
    protected void doSave(Resume resume, Resume key) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Resume key) {
        return key;
    }

    @Override
    protected void doDelete(Resume key) {
        map.remove(key.getUuid());
    }

    @Override
    protected boolean checkKey(Resume key) {
        return key == null;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected List<Resume> getListResume() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }
}
