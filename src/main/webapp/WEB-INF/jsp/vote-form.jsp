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
    <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3><spring:message code="vote.title"/>&nbsp${restaurant.name}</h3>
    <hr/>
    <c:set var="allowed" value="${fn:canRevoteBefore(15, 0) ? '' : 'disabled'}" scope="page"/>
    <form method="post" action="profile/restaurants/${restaurant.id}/votes">
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
            <tr>
                <th><spring:message code="restaurant.name"/></th>
                <th><spring:message code="common.menu"/></th>
                <th><spring:message code="vote.votes"/></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${restaurant.name}</td>
                <td>
                    <button type="button" onclick="window.location.href='profile/restaurants/${restaurant.id}/dishes'">
                        <spring:message code="common.view"/></button>
                </td>
                <td>${fns:length(votes)}</td>
            </tr>
            </tbody>
        </table>
        <br>
        <button type="submit" ${allowed}><spring:message code="common.vote"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="common.cancel"/></button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
