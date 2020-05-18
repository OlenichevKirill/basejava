import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size;

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume resume) {
        storage[size] = resume;
        size++;
    }

    Resume get(String uuid) {
        Resume res = null;
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                res = storage[i];
                break;
            }
        }
        return res;
    }

    void delete(String uuid) {
        int num = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                num = i;
                break;
            }
        }
        if (num >= 0) {
            for (int i = num; i < size; i++) {
                storage[i] = storage[i + 1];
            }
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }
}
