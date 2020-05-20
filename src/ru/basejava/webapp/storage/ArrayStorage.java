package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (storage.length > size) {
            if (!checkResume(resume.toString())) {
                storage[size] = resume;
                size++;
            } else {
                System.out.println("Такое резюме уже есть");
            }
        } else {
            System.out.println("Область хранения массива переполнена");
        }
    }

    public Resume get(String uuid) {
        Resume res = null;
        for (int i = 0; i < size; i++) {
            if (storage[i].toString() == uuid) {
                res = storage[i];
                break;
            }
        }
        return res;
    }

    public void delete(String uuid) {
        if (checkResume(uuid)) {
            for (int i = 0; i < size; i++) {
                if (storage[i].toString() == uuid) {
                    storage[i] = storage[size - 1];
                    storage[size - 1] = null;
                    size--;
                }
            }
        } else {
            System.out.println("Не найдено резюме для удаления");
        }
    }

    public void update(Resume resume) {
        if (checkResume(resume.toString())) {
            System.out.println("Найдено резюме для редактирования");
        } else {
            System.out.println("Резюме для редактирования отсутсвует");
        }
    }

    private boolean checkResume(String uuid) {
        if (get(uuid) != null) {
            return true;
        } else {
            return false;
        }
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
