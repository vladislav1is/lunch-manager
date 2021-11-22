<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <c:set var="isNew" value="${dish.isNew() ? 'dish.create' : 'dish.edit'}"/>
    <title><spring:message code="${isNew}"/></title>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3><spring:message code="${isNew}"/></h3>
    <form method="post" action="admin/restaurants/${restaurantId}/dishes">
        <input type="hidden" name="id" value="${dish.id}" required>
        <dl>
            <dt><spring:message code="dish.registered"/>:</dt>
            <dd>
                <label>
                    <input type="date" value="${dish.registered}" name="registered" required>
                </label>
            </dd>
        </dl>
        <dl>
            <dt><spring:message code="dish.name"/>:</dt>
            <dd>
                <label>
                    <input type="text" value="${dish.name}" size=40 name="name" required>
                </label>
            </dd>
        </dl>
        <dl>
            <dt><spring:message code="dish.price"/>:</dt>
            <dd>
                <label>
                    <input type="number" value="${dish.price}" min="10" max="10000" name="price" required>
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
