<%@ page import="ru.basejava.webapp.model.TextSection" %>
<%@ page import="ru.basejava.webapp.model.ListSection" %>
<%@ page import="ru.basejava.webapp.model.InstitutionSection" %>
<%@ page import="ru.basejava.webapp.util.HtmlUtil" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.basejava.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.basejava.webapp.model.SectionType, ru.basejava.webapp.model.AbstractSection>"/>
    <h3>${sectionEntry.key.title}</h3>
    <c:choose>
        <c:when test="${sectionEntry.key.name() == 'PERSONAL' || sectionEntry.key.name() == 'OBJECTIVE'}">
            <%=((TextSection) sectionEntry.getValue()).getText()%><br/>
        </c:when>
        <c:when test="${sectionEntry.key.name() == 'ACHIEVEMENT' || sectionEntry.key.name() == 'QUALIFICATIONS'}">
            <c:forEach var="list" items="<%=((ListSection) sectionEntry.getValue()).getList()%>">
                <ul>
                    <li>${list}</li>
                </ul>
            </c:forEach>
        </c:when>
        <c:when test="${sectionEntry.key.name() == 'EXPERIENCE' || sectionEntry.key.name() == 'EDUCATION'}">
            <c:forEach var="org" items="<%=((InstitutionSection) sectionEntry.getValue()).getInstitutions()%>">
                <c:choose>
                    <c:when test="${org.homePage.url == null || org.homePage.url.trim().length() == 0}">
                        <h4>${org.homePage.name}</h4>
                    </c:when>
                    <c:otherwise>
                        <h4><a href="${org.homePage.url}">${org.homePage.name}</a></h4>
                    </c:otherwise>
                </c:choose>
                <c:forEach var="pos" items="${org.positions}">
                    <jsp:useBean id="pos" type="ru.basejava.webapp.model.Institution.Position"/>
                    <%=HtmlUtil.formatDates(pos)%>
                    <b>${pos.title}</b><br/>
                    ${pos.description}<br/>
                </c:forEach>
            </c:forEach>
        </c:when>

    </c:choose>
    </c:forEach>
    <button onclick="window.history.back()">Вернуться назад</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

