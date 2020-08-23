<%@ page import="ru.basejava.webapp.model.ContactType" %>
<%@ page import="ru.basejava.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.basejava.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <jsp:useBean id="sectionType" type="ru.basejava.webapp.model.SectionType"/>
            <dl>
                <dt>${sectionType.title}</dt>
                <c:choose>
                    <c:when test="${sectionType.name() == 'PERSONAL' || sectionType.name() == 'OBJECTIVE'}">
                        <dd><textarea cols="120" rows="3" name="${sectionType.name()}">${resume.getSection(sectionType).toString()}</textarea>
                        </dd>
                    </c:when>
                    <c:when test="${sectionType.name() == 'ACHIEVEMENT' || sectionType.name() == 'QUALIFICATIONS'}">
                        <dd>
                            <textarea cols="120" rows="10" name="${sectionType.name()}">${resume.getSection(sectionType).toString()}</textarea>
                        </dd>
                    </c:when>
                </c:choose>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <input type="button" onclick="history.back(); return false;" value="Назад"/>
        <button type="button" onclick="history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

