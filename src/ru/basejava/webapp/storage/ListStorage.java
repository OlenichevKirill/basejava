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
    protected Integer getIndexResume(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doUpdate(Object index, Resume resume) {
        list.set((Integer) index, resume);
    }

    @Override
    protected void doSave(Resume resume, Object index) {
        list.add(resume);
    }

    @Override
    protected Resume doGet(Object index) {
        return list.get((Integer) index);
    }

    @Override
    protected void doDelete(Object index) {
        int num = (Integer) index;
        list.remove(num);
    }

    @Override
    protected boolean checkIndex(Object index) {
        if ((Integer) index < 0) {
            return true;
        } else {
            return false;
        }
    }

}
