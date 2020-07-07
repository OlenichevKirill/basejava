package ru.basejava.webapp.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.basejava.webapp.exception.StorageException;
import ru.basejava.webapp.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void overflowStorage() throws Exception {
        try {
            for (int i = 3; i < 10000; i++) {
                storage.save(new Resume("fullName"));
            }
        } catch (StorageException e) {
            Assert.fail();
        }
        storage.save(new Resume("fullName"));
    }
}