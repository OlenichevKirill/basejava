package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume get(String uuid) {
        Resume res = null;
        int num = getNumResume(uuid);
        if (num >= 0) {
            res = storage[num];
        } else {
            System.out.println("Резюме " + uuid + " отсутсвует");
        }
        return res;
    }

    public void update(Resume resume) {
        int num = getNumResume(resume.toString());
        if (num >= 0) {
            storage[num] = resume;
        } else {
            System.out.println("Резюме " + resume.toString() + " для редактирования отсутствует");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public void delete(String uuid) {
        int num = getNumResume(uuid);
        if (num >= 0) {
            deleteElementStorage(num);
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Резюме " + uuid + " не найдено для удаления");
        }
    }

    public void save(Resume resume) {
        int num = getNumResume(resume.toString());
        if (storage.length > size) {
            if (num < 0) {
                saveElementStorage(resume, num);
                size++;
            } else {
                System.out.println("Резюме " + resume.toString() + " уже есть");
            }
        } else {
            System.out.println("Область хранения массива переполнена");
        }
    }

    protected abstract int getNumResume(String uuid);

    protected abstract void deleteElementStorage(int position);

    protected abstract void saveElementStorage(Resume resume, int position);
}
