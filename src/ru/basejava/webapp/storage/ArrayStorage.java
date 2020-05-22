package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (storage.length > size) {
            if (getNumResume(resume.toString()) == -1) {
                storage[size] = resume;
                size++;
            } else {
                System.out.println("Резюме " + resume.toString() + " уже есть");
            }
        } else {
            System.out.println("Область хранения массива переполнена");
        }
    }

    public Resume get(String uuid) {
        Resume res = null;
        int num = getNumResume(uuid);
        if (num != -1) {
            res = storage[num];
        } else {
            System.out.println("Резюме " + uuid + " отсутсвует");
        }
        return res;
    }

    public void delete(String uuid) {
        int num = getNumResume(uuid);
        if (num != -1) {
            storage[num] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Резюме " + uuid + " не найдено для удаления");
        }
    }

    public void update(Resume resume) {
        int num = getNumResume(resume.toString());
        if (num != -1) {
            storage[num] = resume;
        } else {
            System.out.println("Резюме " + resume.toString() + " для редактирования отсутствует");
        }
    }

    private int getNumResume(String uuid) {
        int num = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                num = i;
            }
        }
        return num;
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
