package ru.basejava.webapp.storage;

import ru.basejava.webapp.storage.SerializationStrategy.ObjectStreamStorage;

public class ObjectStreamStorageTest extends AbstractStorageTest{
    public ObjectStreamStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStorage()));
    }
}
