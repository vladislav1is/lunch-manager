const dishAjaxUrl = (() => {
    let url = $(location).attr('pathname');
    let regexp = /restaurants\/\d+\/dishes/;
    let result = url.match(regexp)[0];

    let restaurantId = result.split('/')[1];
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
    makeEditable({
        "columns": [
            {
                "data": "registered",
                "render": function (date, type, row) {
                    if (type === "display") {
                        return date.substring(0, 10);
                    }
                    return date;
                }
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
    });
});