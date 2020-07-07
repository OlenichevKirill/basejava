package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {

    private final Map<String, Resume> map = new HashMap<>();

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String key) {
        return map.containsKey(key);
    }

    @Override
    protected void doUpdate(String key, Resume resume) {
        map.put(key, resume);
    }

    @Override
    protected void doSave(Resume resume, String key) {
        map.put(key, resume);
    }

    @Override
    protected Resume doGet(String key) {
        return map.get(key);
    }

    @Override
    protected void doDelete(String key) {
        map.remove(key);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }
}
