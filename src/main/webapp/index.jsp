<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="messages.app"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<br>
<section>
    <form method="post" action="users">
        <fmt:message key="app.login"/>: <label> <select name="userId">
        <option value="100001">Admin1</option>
        <option value="100002">Admin2</option>
        <option value="100003" selected>User</option>
    </select>
    </label>
        <button type="submit"><fmt:message key="common.select"/></button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>