<%--@elvariable id="restaurant" type="com.redfox.lunchmanager.model.Restaurant"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:setBundle basename="messages.app"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%-- restaurant.new` cause javax.el.ELException - bug tomcat --%>
    <%-- <title><fmt:message key="${restaurant.isNew() ? 'restaurant.create' : 'restaurant.edit'}"/></title> --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <%-- restaurant.new` cause javax.el.ELException - bug tomcat --%>
    <%-- <h3>${restaurant.new ? 'restaurant.create'  : 'restaurant.edit'}/></h3> --%>
    <form method="post" action="${pageContext.request.contextPath}/admin/restaurants">
        <input type="hidden" name="id" value="${restaurant.id}" required>
        <dl>
            <dt>Name:</dt>
            <dd>
                <label>
                    <input type="text" value="${restaurant.name}" size=40 name="title" required>
                </label>
            </dd>
        </dl>
        <button type="submit"><fmt:message key="common.save"/></button>
        <button onclick="window.history.back()" type="button"><fmt:message key="common.cancel"/></button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
