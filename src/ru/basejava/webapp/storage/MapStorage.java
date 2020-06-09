package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected String getKeyResume(String uuid) {
        return uuid;
    }

    @Override
    protected boolean checkKey(Object key) {
        return !map.containsKey(key);
    }

    @Override
    protected void doUpdate(Object key, Resume resume) {
        map.put((String) key, resume);
    }

    @Override
    protected void doSave(Resume resume, Object key) {
        map.put((String) key, resume);
    }

    @Override
    protected Resume doGet(Object key) {
        return map.get(key);
    }

    @Override
    protected void doDelete(Object key) {
        map.remove(key);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return map.size();
    }
}
