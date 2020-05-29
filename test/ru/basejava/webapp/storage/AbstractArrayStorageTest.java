package ru.basejava.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.basejava.webapp.exception.ExistStorageException;
import ru.basejava.webapp.exception.NotExistStorageException;
import ru.basejava.webapp.exception.StorageException;
import ru.basejava.webapp.model.Resume;

public class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private Resume resume1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid2";
    private Resume resume2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid3";
    private Resume resume3 = new Resume(UUID_3);
    private static final String UUID_4 = "uuid4";
    private Resume resume4 = new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void getAll() {
        Resume[] arrayResume = storage.getAll();
        Assert.assertEquals(3, arrayResume.length);
        Assert.assertEquals(resume1, arrayResume[0]);
        Assert.assertEquals(resume2, arrayResume[1]);
        Assert.assertEquals(resume3, arrayResume[2]);
    }

    @Test
    public void get() {
        Resume resumeTest = storage.get("uuid1");
        Assert.assertSame(resume1, resumeTest);
    }

    @Test
    public void update() {
        Resume resumeTest = new Resume(UUID_2);
        storage.update(resumeTest);
        Assert.assertEquals(resumeTest, storage.get(UUID_2));
        Assert.assertEquals(resume2, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test
    public void save() {
        storage.save(resume4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(resume4, storage.get(UUID_4));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(resume1);
    }

    @Test(expected = StorageException.class)
    public void overflowStorage() throws Exception {
        try {
            for (int i = 3; i < 10000; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail();
        }
        storage.save(new Resume());
    }
}