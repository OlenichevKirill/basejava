package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getNumResume(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void deleteElementStorage(int position) {
        System.arraycopy(storage, position + 1, storage, position, size - position - 1);
    }

    @Override
    protected void saveElementStorage(Resume resume, int position) {
        int pos = -(position + 1);
        System.arraycopy(storage, pos, storage, pos + 1, size - pos);
        storage[pos] = resume;
    }
}
