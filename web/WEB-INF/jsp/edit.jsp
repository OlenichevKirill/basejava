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
                <dt><h4>${sectionType.title}</h4></dt>
                <c:choose>
                    <c:when test="${sectionType.name() == 'PERSONAL' || sectionType.name() == 'OBJECTIVE'}">
                        <dd><textarea cols="120" rows="3"
                                      name="${sectionType.name()}">${resume.getSection(sectionType).toString()}</textarea>
                        </dd>
                    </c:when>
                    <c:when test="${sectionType.name() == 'ACHIEVEMENT' || sectionType.name() == 'QUALIFICATIONS'}">
                        <dd>
                            <textarea cols="120" rows="10"
                                      name="${sectionType.name()}">${resume.getSection(sectionType).toString()}</textarea>
                        </dd>
                    </c:when>
                    <c:when test="${sectionType.name() == 'EXPERIENCE' || sectionType.name() == 'EDUCATION'}">
                        <c:forEach var="org" items="${(resume.getSection(sectionType)).getInstitutions()}" varStatus="number">
                            <dl>
                                <dt><b>Организация</b></dt>
                                <dd><input type="text" name="${sectionType.name()}" size=50
                                           value="${org.homePage.name}" placeholder="Для удаления оставить пустым поле"><br></dd>
                            </dl>
                            <dl>
                                <dt> Электронный адрес</dt>
                                <dd><input type="text" name="${sectionType.name()}_url" size=50 value="${org.homePage.url}"><br>
                                </dd>
                            </dl>
                            <c:forEach var="pos" items="${org.positions}">
                                <dl>
                                    <dt>Дата начала</dt>
                                    <dd><input type="text" name="${sectionType.name()}${number.index}_startDate" size=35
                                               value="${pos.startDate}" placeholder="Для удаления позиции оставить пустым"><br></dd>
                                </dl>
                                <dl>
                                    <dt>Дата окончания</dt>
                                    <dd><input type="text" name="${sectionType.name()}${number.index}_endDate" size=35
                                               value="${pos.endDate}" placeholder="Для удаления позиции оставить пустым"><br></dd>
                                </dl>
                                <dl>
                                    <dt>Заголовок</dt>
                                    <dd><input type="text" name="${sectionType.name()}${number.index}_title" size=50
                                               value="${pos.title}" placeholder="Для удаления позиции оставить пустым поле"><br></dd>
                                </dl>
                                <dl>
                                    <dt>Описание</dt>
                                    <dd><textarea cols="120" rows="10"
                                                  name="${sectionType.name()}${number.index}_description">${pos.description}</textarea></dd>
                                </dl>
                                <br>
                                <br>
                            </c:forEach>
                        </c:forEach>
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

