const restaurantAjaxUrl = "admin/restaurants/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: restaurantAjaxUrl,
    updateTable: function () {
        $.get(restaurantAjaxUrl, updateTableByData);
    }
}

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "defaultContent": "" +
                        "<button type=\"button\" onclick=\"window.location.href='admin/restaurants/${id}/dishes'\"\n" +
                        "           class=\"btn btn-sm btn-secondary\"><spring:message code=\"common.edit\"/>\n" +
                        "</button>"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});