package ru.basejava.webapp;

import ru.basejava.webapp.model.*;
import ru.basejava.webapp.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static ru.basejava.webapp.util.DateUtil.NOW;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        System.out.println(resume.getFullName());
        System.out.println("");

        // Занесение и вывод контактов
        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        resume.addContact(ContactType.HOMEPAGE, "http://gkislin.ru");

        for (Map.Entry<ContactType, String> entry : resume.getContact().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + entry.getValue());
        }

        System.out.println("");

        // Занесение и вывод секций
        resume.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        List<String> achievement = new ArrayList<>();
        achievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(achievement));

        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(qualifications));

        Institution institution1 = new Institution(new Link("Java Online Projects","http://javaops.ru"), Arrays.asList(new Institution.Position(DateUtil.of(2013, Month.NOVEMBER), LocalDate.now(),"Автор проекта.","Создание, организация и проведение Java онлайн проектов и стажировок.")));
        Institution institution2 = new Institution(new Link("Wrike", "https://www.wrike.com"), Arrays.asList(new Institution.Position(DateUtil.of(2012, Month.APRIL), DateUtil.of(2016, Month.JANUARY),"Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring,MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")));
        Institution institution3 = new Institution(new Link("RIT Center",""), Arrays.asList(new Institution.Position(DateUtil.of(2014, Month.NOVEMBER),DateUtil.of(2014, Month.NOVEMBER),"Java архитектор.","Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python")));
        List<Institution> institutions = Arrays.asList(institution1, institution2, institution3);
        resume.addSection(SectionType.EXPERIENCE, new InstitutionSection(institutions));

        Institution institutionEducation1 = new Institution(new Link("Coursera","https://www.coursera.org/learn/progfun1"), Arrays.asList( new Institution.Position(DateUtil.of(2013, Month.MARCH), DateUtil.of(2013, Month.MAY),"","")));
        Institution.Position pos1 = new Institution.Position(DateUtil.of(1987, Month.SEPTEMBER),DateUtil.of(1993, Month.JUNE),"Инженер (программист Fortran, C)","");
        Institution.Position pos2 = new Institution.Position(DateUtil.of(1993, Month.SEPTEMBER),DateUtil.of(1996, Month.JUNE),"Аспирантура (программист С, С++)","");
        Institution institutionEducation2 = new Institution(new Link("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики","https://itmo.ru/ru/"), Arrays.asList(pos1, pos2));
        List<Institution> institutionsEducation = Arrays.asList(institutionEducation1, institutionEducation2);
        resume.addSection(SectionType.EDUCATION, new InstitutionSection(institutionsEducation));

        for (Map.Entry<SectionType, AbstractSection> entry : resume.getSection().entrySet()) {
            System.out.println(entry.getKey().getTitle());
            System.out.println(entry.getValue().toString());
            System.out.println("");
        }
    }

    public static Resume createResume(String uuid,String fullName) {
        Resume resume = new Resume(uuid,fullName);
        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        resume.addContact(ContactType.HOMEPAGE, "http://gkislin.ru");

        resume.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        List<String> achievement = new ArrayList<>();
        achievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(achievement));

        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(qualifications));

        Institution institution1 = new Institution(new Link("Java Online Projects","http://javaops.ru"), Arrays.asList(new Institution.Position(DateUtil.of(2013, Month.NOVEMBER), NOW,"Автор проекта.","Создание, организация и проведение Java онлайн проектов и стажировок.")));
        Institution institution2 = new Institution(new Link("Wrike", "https://www.wrike.com"), Arrays.asList(new Institution.Position(DateUtil.of(2012, Month.APRIL), DateUtil.of(2016, Month.JANUARY),"Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring,MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")));
        Institution institution3 = new Institution(new Link("RIT Center",""), Arrays.asList(new Institution.Position(DateUtil.of(2014, Month.NOVEMBER),DateUtil.of(2014, Month.NOVEMBER),"Java архитектор.","Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python")));
        List<Institution> institutions = Arrays.asList(institution1, institution2, institution3);
        resume.addSection(SectionType.EXPERIENCE, new InstitutionSection(institutions));

        Institution institutionEducation1 = new Institution(new Link("Coursera","https://www.coursera.org/learn/progfun1"), Arrays.asList( new Institution.Position(DateUtil.of(2013, Month.MARCH), DateUtil.of(2013, Month.MAY),"","")));
        Institution.Position pos1 = new Institution.Position(DateUtil.of(1987, Month.SEPTEMBER),DateUtil.of(1993, Month.JUNE),"Инженер (программист Fortran, C)","");
        Institution.Position pos2 = new Institution.Position(DateUtil.of(1993, Month.SEPTEMBER),DateUtil.of(1996, Month.JUNE),"Аспирантура (программист С, С++)","");
        Institution institutionEducation2 = new Institution(new Link("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики","https://itmo.ru/ru/"), Arrays.asList(pos1, pos2));
        List<Institution> institutionsEducation = Arrays.asList(institutionEducation1, institutionEducation2);
        resume.addSection(SectionType.EDUCATION, new InstitutionSection(institutionsEducation));

        return resume;
    }
}
