package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {

    Map<String, Resume> map = new HashMap<>();

    @Override
    protected Resume getKeyResume(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected void doUpdate(Object key, Resume resume) {
        map.put(key.toString(), resume);
    }

    @Override
    protected void doSave(Resume resume, Object key) {
        map.put(resume.toString(), resume);
    }

    @Override
    protected Resume doGet(Object key) {
        return (Resume) key;
    }

    @Override
    protected void doDelete(Object key) {
        map.remove(key.toString());
    }

    @Override
    protected boolean checkKey(Object key) {
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
