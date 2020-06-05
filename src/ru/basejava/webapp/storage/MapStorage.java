package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    Map<String, Resume> map = new HashMap<>();

    @Override
    protected int getIndexResume(String uuid) {
        return 0; // uuid;
    }

    @Override
    protected void doUpdate(int index, Resume resume) {
//        map.put();
    }

    @Override
    protected void doSave(Resume resume, int index) {
//        map.put();
    }

    @Override
    protected Resume doGet(int index) {
//        return map.get();
        return null;
    }

    @Override
    protected void doDelete(int index) {
//        map.remove();
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
