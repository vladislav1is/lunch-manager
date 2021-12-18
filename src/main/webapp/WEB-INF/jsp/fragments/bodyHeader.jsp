<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<header>
    <nav class="navbar navbar-dark bg-dark py-0">
        <div class="container">
            <a href="profile/restaurants" title="<spring:message code="app.title"/>" class="navbar-brand">
                <img src="resources/images/icon-fox.png" width="50" alt="<spring:message code="app.title"/>">
                <spring:message code="app.title"/>
            </a>
            <div class="btn-group ">
                <button type="button" onclick="location.href='admin/restaurants';" class="btn btn-secondary">
                    <spring:message code="app.editor"/>
                </button>
                <button type="button" onclick="location.href='users';" class="btn btn-secondary">
                    <spring:message code="user.title"/>
                </button>
                <button type="button" onclick="location.href='#';" class="btn btn-secondary">
                    <span class="fa fa-sign-in"></span>
                </button>
            </div>
        </div>
    </nav>
</header>