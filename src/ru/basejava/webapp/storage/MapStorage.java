package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    Map<String, Resume> map = new HashMap<>();

    @Override
    protected String getIndexResume(String uuid) {
        if (map.containsKey(uuid)) {
            return uuid;
        } else {
            return null;
        }
    }

    @Override
    protected boolean checkIndex(Object index) {
        if (index == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void doUpdate(Object index, Resume resume) {
        map.put((String) index, resume);
    }

    @Override
    protected void doSave(Resume resume, Object index) {
        map.put((String) index, resume);
    }

    @Override
    protected Resume doGet(Object index) {
        return map.get(index);
    }

    @Override
    protected void doDelete(Object index) {
        map.remove(index);
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
