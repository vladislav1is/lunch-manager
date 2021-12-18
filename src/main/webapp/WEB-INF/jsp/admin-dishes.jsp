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

        <form method="get" action="admin/restaurants/${restaurantId}/dishes/filter">
            <div class="form-group row">
                <label for="startDate" class="col-sm-1 col-form-label"><spring:message code="dish.startDate"/>:</label>
                <div class="col-sm-3">
                    <input id="startDate" type="date" name="startDate" value="${param.startDate}" class="form-control">
                </div>
            </div>
            <div class="form-group row">
                <label for="endDate" class="col-sm-1 col-form-label"><spring:message code="dish.endDate"/>:</label>
                <div class="col-sm-3">
                    <input id="endDate" type="date" name="endDate" value="${param.endDate}" class="form-control">
                </div>
            </div>
            <button type="button" onclick="location.href='admin/restaurants/${restaurant.id}/dishes/create'"
                    class="btn btn-primary">
                <span class="fa fa-plus"></span>
                <spring:message code="dish.add"/>
            </button>
            <button type="submit" class="btn btn-primary">
                <spring:message code="dish.filter"/>
            </button>
        </form>

        <table class="table table-striped mt-3">
            <thead>
            <tr>
                <th><spring:message code="dish.registered"/></th>
                <th><spring:message code="dish.name"/></th>
                <th><spring:message code="dish.price"/></th>
                <th colspan="2"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="dish" items="${dishes}">
                <c:set var="id" value="${dish.id}"/>
                <tr>
                    <td>${fns:formatDate(dish.registered)}</td>
                    <td>${dish.name}</td>
                    <td>${dish.price}</td>
                    <td>
                        <a href="admin/restaurants/${restaurant.id}/dishes/update?id=${id}">
                            <span class="fa text-dark fa-pencil"></span>
                        </a>
                    </td>
                    <td>
                        <a href="admin/restaurants/${restaurant.id}/dishes/delete?id=${id}">
                            <span class="fa text-dark fa-remove"></span>
                        </a>
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
