<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <c:set var="title" value="${dish.isNew() ? 'dish.create' : 'dish.edit'}"/>
    <title><spring:message code="${title}"/></title>
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
        <h3 class="text-center"><spring:message code="${title}"/></h3>

        <form method="post" action="admin/restaurants/${restaurantId}/dishes">
            <input type="hidden" name="id" value="${dish.id}" required>
            <div class="form-group row">
                <label for="date" class="col-sm-1 col-form-label"><spring:message code="dish.registered"/>:</label>
                <div class="col-sm-3">
                    <input id="date" type="date" name="registered" value="${dish.registered}" required
                           class="form-control">
                </div>
            </div>
            <div class="form-group row">
                <label for="name" class="col-sm-1 col-form-label"><spring:message code="dish.name"/>:</label>
                <div class="col-sm-3">
                    <input id="name" type="text" name="name" value="${dish.name}" minlength="2" maxlength="100" required
                           class="form-control">
                </div>
            </div>
            <div class="form-group row">
                <label for="price" class="col-sm-1 col-form-label"><spring:message code="dish.price"/>:</label>
                <div class="col-sm-3">
                    <input id="price" type="number" name="price" value="${dish.price}" min="10" max="10000" required
                           class="form-control">
                </div>
            </div>
            <button type="submit" class="btn btn-primary">
                <spring:message code="common.save"/>
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
