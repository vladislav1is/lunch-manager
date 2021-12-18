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

    <link rel="stylesheet" href="resources/css/style.css?v=2">
    <link rel="stylesheet" href="webjars/bootstrap/4.6.0-1/css/bootstrap.min.css">
    <link rel="stylesheet" href="webjars/noty/3.1.4/demo/font-awesome/css/font-awesome.min.css">
    <link rel="shortcut icon" href="resources/images/icon-fox.png">
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="restaurant.title"/></h3>

        <button class="btn btn-primary" onclick="location.href='admin/restaurants/create'">
            <span class="fa fa-plus"></span>
            <spring:message code="restaurant.add"/>
        </button>
        <table class="table table-striped mt-3">
            <thead>
            <tr>
                <th><spring:message code="restaurant.name"/></th>
                <th><spring:message code="common.menu"/></th>
                <th colspan="2"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="restaurant" items="${restaurants}">
                <c:set var="id" value="${restaurant.id}"/>
                <tr>
                    <td>${restaurant.name}</td>
                    <td>
                        <button type="button" onclick="window.location.href='admin/restaurants/${id}/dishes'"
                                class="btn btn-sm btn-secondary">
                            <spring:message code="common.edit"/>
                        </button>
                    </td>
                    <td>
                        <a href="admin/restaurants/update?id=${id}"><span class="fa text-dark fa-pencil"></span></a>
                    </td>
                    <td>
                        <a href="admin/restaurants/delete?id=${id}"><span class="fa text-dark fa-remove"></span></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
