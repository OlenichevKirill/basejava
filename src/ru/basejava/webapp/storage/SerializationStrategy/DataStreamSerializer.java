package ru.basejava.webapp.storage.SerializationStrategy;

import ru.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements SerializationStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writeSourceData(dos, contacts.entrySet(), (entry) -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            Map<SectionType, AbstractSection> sections = resume.getSections();
            writeSourceData(dos, sections.entrySet(), (entry) -> {
                SectionType type = entry.getKey();
                AbstractSection section = entry.getValue();
                dos.writeUTF(type.name());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeSourceData(dos, ((ListSection) section).getList(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeSourceData(dos, ((InstitutionSection) section).getInstitutions(), (org) -> {
                            dos.writeUTF(org.getHomePage().getName());
                            dos.writeUTF(org.getHomePage().getUrl());
                            writeSourceData(dos, org.getPositions(), (pos) -> {
                                dos.writeUTF(pos.getStartDate().toString());
                                dos.writeUTF(pos.getEndDate().toString());
                                dos.writeUTF(pos.getTitle());
                                dos.writeUTF(pos.getDescription());
                            });
                        });
                        break;
                }
            });
        }

    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            fillResumeData(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            fillResumeData(dis, () -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.setSection(type, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS: {
                        resume.setSection(type, new ListSection(readSourceData(dis, dis::readUTF)));
                        break;
                    }
                    case EXPERIENCE:
                    case EDUCATION: {
                        resume.setSection(type, new InstitutionSection(readSourceData(dis, () ->
                                new Institution(new Link(dis.readUTF(), dis.readUTF()), readSourceData(dis, () ->
                                        new Institution.Position(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF())))
                        )));
                        break;
                    }
                }
            });
            return resume;
        }
    }

    private <T> void writeSourceData(DataOutputStream dos, Collection<T> collection, WriteData<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            writer.write(t);
        }
    }

    private <T> List<T> readSourceData(DataInputStream dis, ReadData<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int j = 0; j < size; j++) {
            list.add(reader.read());
        }
        return list;
    }

    private void fillResumeData(DataInputStream dis, FillResume filler) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            filler.fill();
        }
    }
}
