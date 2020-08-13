package ru.basejava.webapp.web;

import ru.basejava.webapp.Config;
import ru.basejava.webapp.model.Resume;
import ru.basejava.webapp.storage.SqlStorage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    SqlStorage sqlStorage = Config.get().getSqlStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
/*        String name = request.getParameter("name");
        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + '!');*/

        response.getWriter().write("<html>");
        response.getWriter().write("<head>");
        response.getWriter().write("<title>All resumes</title>");
        response.getWriter().write("</head>");
        response.getWriter().write("<body>");
        response.getWriter().write("<table style=\"width:100 % \">");
        response.getWriter().write("<tr>");
        response.getWriter().write("<th>uuid</th>");
        response.getWriter().write("<th>full_name</th>");
        response.getWriter().write("</tr>");

        for (Resume resume : sqlStorage.getAllSorted()) {
            response.getWriter().write("<tr>");
            response.getWriter().write("<td>" + resume.getUuid() + "</td>");
            response.getWriter().write("<td>" + resume.getFullName() + "</td>");
            response.getWriter().write("</tr>");
        }
//        response.getWriter().write("<tr>");
//        response.getWriter().write("<td>123456</td>");
//        response.getWriter().write("<td>Smith</td>");
//        response.getWriter().write("</tr>");
        response.getWriter().write("</table>");
        response.getWriter().write("</body>");
        response.getWriter().write("</html>");
    }
}
