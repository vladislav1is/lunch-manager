<%--@elvariable id="users" type="java.util.List"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fns" uri="http://lunch-manager.redfox.com/functions" %>

<fmt:setBundle basename="messages.app"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="user.title"/></title>
    <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3><fmt:message key="user.title"/></h3>
    <hr/>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><fmt:message key="user.name"/></th>
            <th><fmt:message key="user.email"/></th>
            <th><fmt:message key="user.roles"/></th>
            <th><fmt:message key="user.active"/></th>
            <th><fmt:message key="user.registered"/></th>
        </tr>
        </thead>
        <c:forEach items="${users}" var="user">
            <jsp:useBean id="user" scope="page" type="com.redfox.lunchmanager.model.User"/>
            <tr>
                <td><c:out value="${user.name}"/></td>
                <td><a href="mailto:${user.email}">${user.email}</a></td>
                <td>${user.roles}</td>
                <td><%=user.isEnabled()%>
                </td>
                <fmt:parseDate value="${fns:formatDateTime(user.registered)}" pattern="yyyy-MM-dd HH:mm"
                               var="myParseDate"/>
                <td><fmt:formatDate value="${myParseDate}" pattern="dd-MMMM-yyyy"/></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>