package ru.basejava.webapp.storage;

import ru.basejava.webapp.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        System.out.println(resume.getFullName());
        System.out.println("");

        // Занесение и вывод контактов
        resume.getContact().put(ContactType.PHONE, "+7(921) 855-0482");
        resume.getContact().put(ContactType.SKYPE, "grigory.kislin");
        resume.getContact().put(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.getContact().put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.getContact().put(ContactType.GITHUB, "https://github.com/gkislin");
        resume.getContact().put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        resume.getContact().put(ContactType.HOMEPAGE, "http://gkislin.ru");

        for (Map.Entry<ContactType, String> entry : resume.getContact().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + entry.getValue());
        }

        System.out.println("");

        // Занесение и вывод секций
        resume.getSection().put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.getSection().put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        List<String> achievement = new ArrayList<>();
        achievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        resume.getSection().put(SectionType.ACHIEVEMENT, new ListSection(achievement));

        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        resume.getSection().put(SectionType.QUALIFICATIONS, new ListSection(qualifications));

        Institution institution1 = new Institution("Java Online Projects", "10/2013", "Сейчас", "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.");
        Institution institution2 = new Institution("Wrike", "10/2014", "01/2016", "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring,MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        Institution institution3 = new Institution("RIT Center", "04/2012", "10/2014", "Java архитектор.", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        List<Institution> institutions = Arrays.asList(institution1, institution2, institution3);
        resume.getSection().put(SectionType.EXPERIENCE, new InstitutionSection(institutions));

        Institution institutionEducation1 = new Institution("Coursera", "03/2013", "05/2013", "\"Functional Programming Principles in Scala\" by Martin Odersky", "");
        Institution institutionEducation2 = new Institution("Luxoft", "03/2011", "04/2011", "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", "");
        Institution institutionEducation3 = new Institution("Siemens AG", "01/2005", "04/2005", "3 месяца обучения мобильным IN сетям (Берлин)", "");
        List<Institution> institutionsEducation = Arrays.asList(institutionEducation1, institutionEducation2, institutionEducation3);
        resume.getSection().put(SectionType.EDUCATION, new InstitutionSection(institutionsEducation));

        for (Map.Entry<SectionType, Section> entry : resume.getSection().entrySet()) {
            System.out.println(entry.getKey().getTitle());
            System.out.println(entry.getValue().toString());
            System.out.println("");
        }
    }
}
