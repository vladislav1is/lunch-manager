<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/javascript" defer>
    const i18n = [];

    <%-- user.add/user.edit or restaurant.add/restaurant.edit or dish.add/dish.edit --%>
    i18n["addTitle"] = '<spring:message code="${param.page}.add"/>';
    i18n["editTitle"] = '<spring:message code="${param.page}.edit"/>';

    <c:forEach var="key" items='<%=new String[]{"common.deleted","common.saved","common.enabled","common.disabled","common.search","common.confirm", "common.edit", "common.view"}%>'>
        i18n['${key}'] = "<spring:message code='${key}'/>";
    </c:forEach>
</script>
