package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage{
    List<Resume> list = new ArrayList<Resume>();

    @Override
    protected void doClear() {
        list.clear();
    }

    @Override
    protected int getSize() {
        return list.size();
    }

    @Override
    protected Resume[] doGetAll() {
        List<Resume> listNew = new ArrayList<Resume>(list);
        return (Resume[]) listNew.toArray();
    }

    @Override
    protected int getNumResume(String uuid) {
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
