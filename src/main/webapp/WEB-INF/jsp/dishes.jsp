<%--@elvariable id="restaurants" type="com.redfox.lunchmanager.model.java.util.List"--%>
<%--@elvariable id="restaurant" type="com.redfox.lunchmanager.to.RestaurantTo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fns" uri="http://lunch-manager.redfox.com/functions" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><spring:message code="dish.title"/>&nbsp${restaurant.name}</title>
    <base href="${pageContext.request.contextPath}/"/>

    <link rel="stylesheet" href="resources/css/style.css?v=2">
    <link rel="stylesheet" href="webjars/bootstrap/4.6.0-1/css/bootstrap.min.css">
    <link rel="stylesheet" href="webjars/noty/3.1.4/demo/font-awesome/css/font-awesome.min.css">
    <link rel="shortcut icon" href="resources/images/icon-fox.png">
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="dish.title"/>&nbsp${restaurant.name}</h3>

        <table class="table table-striped mt-3">
            <thead>
            <tr>
                <th><spring:message code="dish.registered"/></th>
                <th><spring:message code="dish.name"/></th>
                <th><spring:message code="dish.price"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="dish" items="${dishes}">
                <c:set var="id" value="${dish.id}"/>
                <tr>
                    <td>${fns:formatDate(dish.registered)}</td>
                    <td>${dish.name}</td>
                    <td>${dish.price}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
