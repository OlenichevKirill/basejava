package ru.basejava.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.basejava.webapp.ResumeTestData;
import ru.basejava.webapp.exception.ExistStorageException;
import ru.basejava.webapp.exception.NotExistStorageException;
import ru.basejava.webapp.model.Resume;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("D:\\basejava\\storage");

    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private Resume resume1 = ResumeTestData.createResume(UUID_1, "Tom");
    private static final String UUID_2 = "uuid2";
    private Resume resume2 = ResumeTestData.createResume(UUID_2, "Bill");
    private static final String UUID_3 = "uuid3";
    private Resume resume3 = ResumeTestData.createResume(UUID_3, "Tom");
    private static final String UUID_4 = "uuid4";
    private Resume resume4 = ResumeTestData.createResume(UUID_4, "Kirill");


    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resume3);
        storage.save(resume2);
        storage.save(resume1);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void getAllSorted() {
        List<Resume> actualResumes = storage.getAllSorted();
        List<Resume> expectedResumes = Arrays.asList(resume2, resume1, resume3);
        Assert.assertEquals(expectedResumes, actualResumes);
    }

    @Test
    public void get() {
        Resume resumeTest = storage.get(UUID_1);
        Assert.assertEquals(resume1, resumeTest);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_4);
    }

    @Test
    public void update() {
        Resume resumeTest = new Resume(UUID_2, "Bill");
        storage.update(resumeTest);
        Assert.assertEquals(resumeTest, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(resume4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(UUID_4);
    }

    @Test
    public void save() {
        storage.save(resume4);
        assertSize(4);
        Assert.assertEquals(resume4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(resume1);
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }
}