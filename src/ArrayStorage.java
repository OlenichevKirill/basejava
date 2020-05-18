import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, 0, size(), null);
    }

    void save(Resume resume) {
        storage[size()] = resume;
    }

    Resume get(String uuid) {
        Resume res = null;
        for (int i = 0; i < size(); i++) {
            if (storage[i].toString() == uuid) {
                res = storage[i];
                break;
            }
        }
        return res;
    }

    void delete(String uuid) {
        int num = 0;
        for (int i = 0; i < size(); i++) {
            if (storage[i].toString() == uuid) {
                num = i;
                break;
            }
        }
        for (int i = num; i < size(); i++) {
            storage[i] = storage[i + 1];
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size());
    }

    int size() {
        int size = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                size++;
            } else {
                break;
            }
        }
        return size;
    }
}
