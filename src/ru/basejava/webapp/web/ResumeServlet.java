package ru.basejava.webapp.web;

import ru.basejava.webapp.Config;
import ru.basejava.webapp.model.Resume;
import ru.basejava.webapp.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    SqlStorage sqlStorage = Config.get().getSqlStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
//        response.getWriter().write("<td>ae6b9a27-c443-4826-9bcf-4d43ac3b6281</td>");
//        response.getWriter().write("<td>Tom</td>");
//        response.getWriter().write("</tr>");
//                response.getWriter().write("<tr>");
//        response.getWriter().write("<td>0b815879-06c1-418c-83cb-a40db2e737e5</td>");
//        response.getWriter().write("<td>Bill</td>");
//        response.getWriter().write("</tr>");
//                response.getWriter().write("<tr>");
//        response.getWriter().write("<td>74c08cae-4a8c-4e54-9195-ff3e1384e4c9</td>");
//        response.getWriter().write("<td>Tom</td>");
//        response.getWriter().write("</tr>");
        response.getWriter().write("</table>");
        response.getWriter().write("</body>");
        response.getWriter().write("</html>");
    }
}
