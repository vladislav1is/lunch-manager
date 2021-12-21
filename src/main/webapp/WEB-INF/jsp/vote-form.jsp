<%--@elvariable id="restaurants" type="com.redfox.lunchmanager.model.java.util.List"--%>
<%--@elvariable id="restaurant" type="com.redfox.lunchmanager.to.RestaurantTo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://lunch-manager.redfox.com/functions" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><spring:message code="vote.title"/>&nbsp${restaurant.name}</title>
    <base href="${pageContext.request.contextPath}/"/>

    <link rel="stylesheet" href="resources/css/style.css?v=2">
    <link rel="stylesheet" href="webjars/bootstrap/4.6.0-1/css/bootstrap.min.css">
    <link rel="stylesheet" href="webjars/noty/3.1.4/demo/font-awesome/css/font-awesome.min.css">
    <link rel="shortcut icon" href="resources/images/icon-fox.png"></head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron pt-4">
    <div class="container">
        <h3><spring:message code="vote.title"/>&nbsp${restaurant.name}</h3>

        <c:set var="allowed" value="${fn:canRevoteBefore(15, 0) ? '' : 'disabled'}" scope="page"/>
        <form method="post" action="restaurants/${restaurant.id}/votes">
            <table class="table table-striped mt-3">
                <thead>
                <tr>
                    <th><spring:message code="restaurant.name"/></th>
                    <th><spring:message code="restaurant.menu"/></th>
                    <th><spring:message code="common.visitors"/></th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${restaurant.name}</td>
                    <td>
                        <button type="button" onclick="window.location.href='restaurants/${restaurant.id}/dishes'"
                                class="btn btn-sm btn-secondary">
                            <spring:message code="common.view"/></button>
                    </td>
                    <td>${fns:length(votes)}</td>
                </tr>
                </tbody>
            </table>
            <button type="submit" ${allowed} class="btn btn-primary">
                <spring:message code="common.vote"/>
            </button>
            <button type="button" onclick="window.history.back()" class="btn btn-primary">
                <spring:message code="common.cancel"/>
            </button>
        </form>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
