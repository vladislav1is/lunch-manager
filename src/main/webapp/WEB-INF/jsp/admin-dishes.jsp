<%--@elvariable id="restaurants" type="com.redfox.lunchmanager.model.java.util.List"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fns" uri="http://lunch-manager.redfox.com/functions" %>

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
    <h3><spring:message code="dish.title"/></h3>
    <form method="get" action="admin/restaurants/${restaurantId}/dishes/filter">
        <dl>
            <dt><spring:message code="dish.startDate"/>:</dt>
            <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="dish.endDate"/>:</dt>
            <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
        </dl>
        <button type="submit"><spring:message code="dish.filter"/></button>
    </form>
    <hr/>
    <p>
        <a href="admin/restaurants/${restaurantId}/dishes/create"><spring:message
                code="dish.add"/></a><br>
    </p>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><spring:message code="dish.registered"/></th>
            <th><spring:message code="dish.name"/></th>
            <th><spring:message code="dish.price"/></th>
            <th colspan="2"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="dish" items="${dishes}" varStatus="status">
            <c:set var="id" value="${dish.id}"/>
            <tr>
                <td>${fns:formatDate(dish.registered)}</td>
                <td>${dish.name}</td>
                <td>${dish.price}</td>
                <td>
                    <a href="admin/restaurants/${restaurantId}/dishes/update?id=${id}">
                        <spring:message code="common.update"/>
                    </a>
                </td>
                <td>
                    <a href="admin/restaurants/${restaurantId}/dishes/delete?id=${id}">
                        <spring:message code="common.delete"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
