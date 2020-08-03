package ru.basejava.webapp.storage;

import ru.basejava.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(Config.get().getSqlStorage());
    }
}
