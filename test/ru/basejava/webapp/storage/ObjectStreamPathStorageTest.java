package ru.basejava.webapp.storage;

import ru.basejava.webapp.storage.SerializationStrategy.ObjectStreamStorage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest{
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamStorage()));
    }
}
