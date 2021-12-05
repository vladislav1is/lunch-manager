<%--@elvariable id="restaurants" type="com.redfox.lunchmanager.model.java.util.List"--%>
<%--@elvariable id="restaurant" type="com.redfox.lunchmanager.to.RestaurantTo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><spring:message code="restaurant.title"/></title>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3><spring:message code="restaurant.title"/></h3>
    <hr/>
    <p>
        <a href="admin/restaurants/create">
            <spring:message code="restaurant.add"/></a><br>
    </p>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><spring:message code="restaurant.name"/></th>
            <th><spring:message code="common.menu"/></th>
            <th colspan="2"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="restaurant" items="${restaurants}" varStatus="status">
            <c:set var="id" value="${restaurant.id}"/>
            <tr>
                <td>${restaurant.name}</td>
                <td>
                    <button type="button" onclick="window.location.href='admin/restaurants/${id}/dishes'">
                        <spring:message code="common.edit"/></button>
                </td>
                <td><a href="admin/restaurants/update?id=${id}"><spring:message code="common.update"/></a></td>
                <td><a href="admin/restaurants/delete?id=${id}"><spring:message code="common.delete"/></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
