<%--@elvariable id="restaurants" type="com.redfox.lunchmanager.model.java.util.List"--%>
<%--@elvariable id="restaurant" type="com.redfox.lunchmanager.model.Restaurant"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:setBundle basename="messages.app"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="restaurant.title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3><fmt:message key="restaurant.title"/></h3>
    <hr/>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><fmt:message key="restaurant.name"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="restaurant" items="${restaurants}" varStatus="status">
            <c:set var="id" value="${restaurant.id}"/>
            <tr>
                <td>${restaurant.name}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
