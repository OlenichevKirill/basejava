package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getNumResume(String uuid) {
        int num = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                num = i;
            }
        }
        return num;
    }

    @Override
    protected void deleteElementStorage(int num) {
        storage[num] = storage[size - 1];
    }

    @Override
    protected void saveElementStorage(Resume resume, int position) {
        storage[size] = resume;
    }


}
