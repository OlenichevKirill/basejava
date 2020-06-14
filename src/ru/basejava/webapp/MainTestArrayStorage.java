package ru.basejava.webapp;

import ru.basejava.webapp.model.Resume;
import ru.basejava.webapp.storage.*;

/**
 * Test for your ru.basejava.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "Abram");
        Resume r2 = new Resume("uuid2", "Bill");
        Resume r3 = new Resume("uuid3", "Tom");
        Resume r4 = new Resume("uuid4", "Tom");
        Resume r5 = new Resume("uuid1", "Abram");

        System.out.println(r1 == r5);

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r4);
        ARRAY_STORAGE.save(r3);
        System.out.println("Size: " + ARRAY_STORAGE.size());
        printAll();
//        ARRAY_STORAGE.save(r1);
        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));

        ARRAY_STORAGE.save(r2);
        System.out.println("Size: " + ARRAY_STORAGE.size());
        printAll();
        ARRAY_STORAGE.delete("uuid2");
        printAll();
//        ARRAY_STORAGE.update(r5);
//        ARRAY_STORAGE.delete("uuid5");

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.toString()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

//        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.toString());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted().toArray(new Resume[0])) {
            System.out.println(r);
        }
    }
}
