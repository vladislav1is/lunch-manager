<%--@elvariable id="restaurant" type="com.redfox.lunchmanager.to.RestaurantTo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fns" uri="http://lunch-manager.redfox.com/functions" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><spring:message code="dish.editor"/>&nbsp${restaurant.name}</title>
    <base href="${pageContext.request.contextPath}/"/>

    <jsp:include page="fragments/libs.jsp"/>
</head>
<body>
<script type="text/javascript" src="resources/js/lunchmanager.common.js" defer></script>
<script type="text/javascript" src="resources/js/lunchmanager.dishes-editor.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="dish.title"/>&nbsp${restaurant.name}</h3>

        <%-- https://getbootstrap.com/docs/4.0/components/card/ --%>
        <div class="card border-dark">
            <div class="card-body pb-0">
                <form id="filter">
                    <div class="row">
                        <div class="col-3">
                            <label for="startDate"><spring:message code="dish.startDate"/></label>
                            <input class="form-control" name="startDate" id="startDate" autocomplete="off">
                        </div>
                        <div class="col-3">
                            <label for="endDate"><spring:message code="dish.endDate"/></label>
                            <input class="form-control" name="endDate" id="endDate" autocomplete="off">
                        </div>
                    </div>
                </form>
            </div>
            <div class="card-footer text-right">
                <button class="btn btn-danger custom-btn" onclick="clearFilter()">
                    <span class="fa fa-remove"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button class="btn btn-primary" onclick="ctx.updateTable()">
                    <span class="fa fa-filter"></span>
                    <spring:message code="dish.filter"/>
                </button>
            </div>
        </div>
        <br/>
        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            <spring:message code="dish.add"/>
        </button>
        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="dish.registered"/></th>
                <th><spring:message code="dish.name"/></th>
                <th><spring:message code="dish.price"/></th>
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
                        <label for="registered" class="col-form-label"><spring:message code="dish.registered"/></label>
                        <input class="form-control" id="registered" name="registered" autocomplete="off"
                               placeholder="<spring:message code="dish.registered"/>" required>
                    </div>

                    <div class="form-group">
                        <label for="name" class="col-form-label"><spring:message
                                code="dish.name"/></label>
                        <input type="text" class="form-control" id="name" name="name" minlength="2" maxlength="100"
                               placeholder="<spring:message code="dish.name"/>" required>
                    </div>

                    <div class="form-group">
                        <label for="price" class="col-form-label"><spring:message code="dish.price"/></label>
                        <input type="number" class="form-control" id="price" name="price" min="10" max="10000"
                               placeholder="1000" required>
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
    <jsp:param name="page" value="dish"/>
</jsp:include>
</html>
