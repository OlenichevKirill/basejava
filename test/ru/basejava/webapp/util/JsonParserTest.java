package ru.basejava.webapp.util;

import org.junit.Assert;
import org.junit.Test;
import ru.basejava.webapp.ResumeTestData;
import ru.basejava.webapp.model.AbstractSection;
import ru.basejava.webapp.model.Resume;
import ru.basejava.webapp.model.TextSection;

import java.util.UUID;

public class JsonParserTest {

    @Test
    public void testResume() {
        Resume R1 = ResumeTestData.createResume(UUID.randomUUID().toString(), "Bill");
        String json = JsonParser.write(R1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(R1, resume);
    }

    @Test
    public void write() {
        AbstractSection section1 = new TextSection("Objective1");
        String json = JsonParser.write(section1, AbstractSection.class);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(section1, section2);
    }
}