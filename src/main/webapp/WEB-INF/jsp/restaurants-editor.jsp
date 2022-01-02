<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="fragments/csrf.jsp"/>

    <title><spring:message code="restaurant.editor"/></title>
    <base href="${pageContext.request.contextPath}/"/>

    <jsp:include page="fragments/libs.jsp"/>
</head>
<body>
<script type="text/javascript" src="resources/js/lunchmanager.common.js" defer></script>
<script type="text/javascript" src="resources/js/lunchmanager.restaurants-editor.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="restaurant.title"/></h3>

        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            <spring:message code="restaurant.add"/>
        </button>
        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="restaurant.name"/></th>
                <th><spring:message code="restaurant.menu"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="modalTitle"></h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="name" class="col-form-label"><spring:message code="restaurant.name"/></label>
                        <input type="text" minlength="2" maxlength="100" class="form-control" required id="name" name="name"
                               placeholder="<spring:message code="restaurant.name"/>">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="save()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/i18n.jsp">
    <jsp:param name="page" value="restaurant"/>
</jsp:include>
</html>
