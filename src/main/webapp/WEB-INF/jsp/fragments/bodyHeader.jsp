<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<header>
    <nav class="navbar navbar-dark bg-dark py-0">
        <div class="container">
            <a href="restaurants" title="<spring:message code="app.title"/>" class="navbar-brand">
                <img src="resources/images/icon-fox.png" width="50" alt="<spring:message code="app.title"/>">
                <spring:message code="app.title"/>
            </a>
            <sec:authorize access="isAuthenticated()">
                <form:form class="form-inline my-2" action="logout" method="post">
                    <div class="btn-group">
                        <sec:authorize access="hasRole('ADMIN')">
                            <button type="button" onclick="location.href='restaurants/editor';"
                                    class="btn btn-secondary">
                                <spring:message code="app.editor"/>
                            </button>
                            <button type="button" onclick="location.href='users';" class="btn btn-secondary">
                                <spring:message code="user.title"/>
                            </button>
                        </sec:authorize>
                        <a class="btn btn-secondary" href="profile">${userTo.name}</a>
                        <button class="btn btn-danger custom-btn" type="submit">
                            <span class="fa fa-sign-out"></span>
                        </button>
                    </div>
                </form:form>
            </sec:authorize>
            <sec:authorize access="isAnonymous()">
                <form:form class="form-inline my-2" id="login_form" action="spring_security_check" method="post">
                    <div class="input-group my-2">
                        <label>
                            <input class="form-control mr-1" type="text" placeholder="Email" name="username">
                        </label>
                        <label>
                            <input class="form-control mr-1" type="password" placeholder="Password" name="password">
                        </label>
                        <button class="btn btn-danger custom-btn" type="submit">
                            <span class="fa fa-sign-in"></span>
                        </button>
                    </div>
                </form:form>
            </sec:authorize>
        </div>
    </nav>
</header>