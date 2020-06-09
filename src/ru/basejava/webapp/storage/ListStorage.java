package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Resume[] getAll() {
        List<Resume> resumes = new ArrayList<>(list);
        return resumes.toArray(new Resume[0]);
    }

    @Override
    protected Integer getKeyResume(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doUpdate(Object key, Resume resume) {
        list.set((Integer) key, resume);
    }

    @Override
    protected void doSave(Resume resume, Object key) {
        list.add(resume);
    }

    @Override
    protected Resume doGet(Object key) {
        return list.get((Integer) key);
    }

    @Override
    protected void doDelete(Object key) {
        int num = (Integer) key;
        list.remove(num);
    }

    @Override
    protected boolean checkKey(Object key) {
        return (Integer) key < 0;
    }
}
