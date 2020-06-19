package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    protected List<Resume> getListResume() {
        return new ArrayList<>(list);
    }

    @Override
    protected Integer getKeyResume(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doUpdate(Integer key, Resume resume) {
        list.set(key, resume);
    }

    @Override
    protected void doSave(Resume resume, Integer key) {
        list.add(resume);
    }

    @Override
    protected Resume doGet(Integer key) {
        return list.get(key);
    }

    @Override
    protected void doDelete(Integer key) {
        int num = key;
        list.remove(num);
    }

    @Override
    protected boolean checkKey(Integer key) {
        return key < 0;
    }
}
