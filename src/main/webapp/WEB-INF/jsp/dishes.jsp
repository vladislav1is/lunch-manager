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

    <jsp:include page="fragments/libs.jsp"/>
</head>
<body>
<script type="text/javascript" src="resources/js/lunchmanager.common.js" defer></script>
<script type="text/javascript" src="resources/js/lunchmanager.dishes.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="dish.title"/>&nbsp${restaurant.name}</h3>

        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="dish.registered"/></th>
                <th><spring:message code="dish.name"/></th>
                <th><spring:message code="dish.price"/></th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/i18n.jsp">
    <jsp:param name="page" value="dish"/>
</jsp:include>
</html>
