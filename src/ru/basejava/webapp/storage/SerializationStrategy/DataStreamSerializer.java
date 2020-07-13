package ru.basejava.webapp.storage.SerializationStrategy;

import ru.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializationStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            // TODO implements sections
            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType type = entry.getKey();
                String typeName = type.name();
                AbstractSection section = entry.getValue();
                dos.writeUTF(type.name());
                switch (typeName) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        dos.writeUTF(((TextSection) section).getText());
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        List<String> listString = ((ListSection) section).getList();
                        dos.writeInt(listString.size());
                        for (String list : listString) {
                            dos.writeUTF(list);
                        }
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":

                        List<Institution> institutions = ((InstitutionSection) section).getInstitutions();
                        dos.writeInt(institutions.size());
                        for (Institution org : institutions) {
                            dos.writeUTF(org.getHomePage().getName());
                            dos.writeUTF(org.getHomePage().getUrl());

                            List<Institution.Position> positions = org.getPositions();
                            dos.writeInt(positions.size());
                            for (Institution.Position pos : positions) {
                                dos.writeUTF(pos.getStartDate().toString());
                                dos.writeUTF(pos.getEndDate().toString());
                                dos.writeUTF(pos.getTitle());
                                dos.writeUTF(pos.getDescription());
                            }
                        }
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            // TODO implements sections
            int sizeSection = dis.readInt();
            for (int i = 0; i < sizeSection; i++) {
                SectionType type = SectionType.valueOf(dis.readUTF());
                String typeName = type.name();
                switch (typeName) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        resume.addSection(type, new TextSection(dis.readUTF()));
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS": {
                        int sizeList = dis.readInt();
                        List<String> list = new ArrayList<>();
                        for (int j = 0; j < sizeList; j++) {
                            list.add(dis.readUTF());
                        }
                        resume.addSection(type, new ListSection(list));
                        break;
                    }
                    case "EXPERIENCE":
                    case "EDUCATION": {

                        int sizeList = dis.readInt();
                        List<Institution> listOrg = new ArrayList<>();
                        for (int k = 0; k < sizeList; k++) {
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            Link link = new Link(name, url);

                            int sizePos = dis.readInt();
                            List<Institution.Position> listPos = new ArrayList<>();
                            for (int l = 0; l < sizePos; l++) {
                                listPos.add(new Institution.Position(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
                            }
                            listOrg.add(new Institution(link, listPos));
                        }
                        resume.addSection(type, new InstitutionSection(listOrg));
                        break;
                    }
                }
            }
            return resume;
        }
    }
}
