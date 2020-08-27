package ru.basejava.webapp.web;

import ru.basejava.webapp.Config;
import ru.basejava.webapp.model.*;
import ru.basejava.webapp.storage.SqlStorage;
import ru.basejava.webapp.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class ResumeServlet extends HttpServlet {
    SqlStorage sqlStorage; // = Config.get().getSqlStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sqlStorage = Config.get().getSqlStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        Resume resume;
        if (uuid == null || uuid.trim().length() == 0) {
            resume = new Resume(fullName);
        } else {
            resume = sqlStorage.get(uuid);
            resume.setFullName(fullName);
        }


        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (isExist(value)) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] orgName = request.getParameterValues(type.name());
            if (value != null || value.trim().length() != 0 || orgName.length != 0) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> list = new ArrayList<>(Arrays.asList(value.split("\n")));
                        list.removeIf(str -> Objects.equals(str, "\r") || str.trim().length() == 0);
                        resume.addSection(type, new ListSection(list));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        String[] url = request.getParameterValues(type.name() + "_url");
                        List<Institution> institutions = new ArrayList<>();
                        for (int i = 0; i < orgName.length; i++) {
                            if (orgName[i] != null && orgName[i].trim().length() != 0) {
                                String[] startDate = request.getParameterValues(type.name() + i + "_startDate");
                                String[] endDate = request.getParameterValues(type.name() + i + "_endDate");
                                String[] title = request.getParameterValues(type.name() + i + "_title");
                                String[] description = request.getParameterValues(type.name() + i + "_description");
                                List<Institution.Position> positions = new ArrayList<>();

                                for (int j = 0; j < title.length; j++) {
                                    if (isExist(title[j]) && isExist(startDate[j]) && isExist(endDate[j])) {
                                        positions.add(new Institution.Position(LocalDate.parse(startDate[j]), LocalDate.parse(endDate[j]), title[j], description[j]));
                                    }
                                }
                                institutions.add(new Institution(new Link(orgName[i], url[i]), positions));
                            }
                        }
                        resume.addSection(type, new InstitutionSection(institutions));
                        break;
                }
            } else {
                resume.getSections().remove(type);
            }
        }

        if (uuid == null || uuid.trim().length() == 0) {
            sqlStorage.save(resume);
        } else {
            sqlStorage.update(resume);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", sqlStorage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                sqlStorage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "add":
                resume = new Resume();
                List<Institution.Position> pos = new ArrayList<>();
                pos.add(addPosition());
                resume.addSection(SectionType.EDUCATION, new InstitutionSection(new Institution(new Link("", ""), pos)));
                resume.addSection(SectionType.EXPERIENCE, new InstitutionSection(new Institution(new Link("", ""), pos)));
                break;
            case "view":
                resume = sqlStorage.get(uuid);
                break;
            case "edit":
                resume = sqlStorage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    if (type.equals(SectionType.EDUCATION) || type.equals(SectionType.EXPERIENCE)) {
                        InstitutionSection section = (InstitutionSection) resume.getSection(type);
                        List<Institution> institutions = section.getInstitutions();
                        for (Institution inst : institutions) {
                            inst.getPositions().add(addPosition());
                        }
                        List<Institution.Position> position = new ArrayList<>();
                        position.add(addPosition());
                        institutions.add(new Institution(new Link("", ""), position));
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    private boolean isExist(String atr) {
        return atr != null && atr.trim().length() != 0;
    }

    private Institution.Position addPosition() {
        return new Institution.Position(DateUtil.of(1900, Month.JANUARY), DateUtil.of(1900, Month.JANUARY), "", "");
    }
}
