<%--@elvariable id="restaurant" type="com.redfox.lunchmanager.model.Restaurant"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
     <title><spring:message code="${restaurant.isNew() ? 'restaurant.create' : 'restaurant.edit'}"/></title>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
     <h3><spring:message code="${restaurant.isNew() ? 'restaurant.create' : 'restaurant.edit'}"/></h3>
    <form method="post" action="${pageContext.request.contextPath}/admin/restaurants">
        <input type="hidden" name="id" value="${restaurant.id}" required>
        <dl>
            <dt><spring:message code="restaurant.name"/>:</dt>
            <dd>
                <label>
                    <input type="text" value="${restaurant.name}" size=40 name="title" required>
                </label>
            </dd>
        </dl>
        <button type="submit"><spring:message code="common.save"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="common.cancel"/></button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
