const dishAjaxUrl = (() => {
    let restaurantId = $(location).attr('pathname').split('/')[3];
    return "profile/restaurants/" + restaurantId + "/dishes/";
})();

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: dishAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: dishAjaxUrl + "filter",
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "ajax": {
                "url": dishAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "registered"
                },
                {
                    "data": "name"
                },
                {
                    "data": "price"
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});