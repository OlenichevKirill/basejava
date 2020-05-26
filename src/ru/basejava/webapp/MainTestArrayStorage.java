package ru.basejava.webapp;

import ru.basejava.webapp.model.Resume;
import ru.basejava.webapp.storage.ArrayStorage;
import ru.basejava.webapp.storage.SortedArrayStorage;
import ru.basejava.webapp.storage.Storage;

/**
 * Test for your ru.basejava.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid2");
        Resume r3 = new Resume();
        r3.setUuid("uuid3");
        Resume r4 = new Resume();
        r4.setUuid("uuid4");
        Resume r5 = new Resume();
        r5.setUuid("uuid5");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r4);
        ARRAY_STORAGE.save(r3);
        printAll();
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        printAll();
        ARRAY_STORAGE.delete("uuid2");
        printAll();
        ARRAY_STORAGE.update(r5);
        ARRAY_STORAGE.delete("uuid5");

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.toString()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.toString());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
