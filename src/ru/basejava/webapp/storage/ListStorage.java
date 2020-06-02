package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage{
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
        return (Resume[]) resumes.toArray();
    }

    @Override
    protected int getIndexResume(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().equals(uuid)){
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doUpdate(int index, Resume resume) {
        list.set(index, resume);
    }

    @Override
    protected void doSave(Resume resume, int index) {
        list.add(resume)
;    }

    @Override
    protected Resume doGet(int index) {
        return list.get(index);
    }

    @Override
    protected void doDelete(int index) {
        list.remove(index);
    }

}
