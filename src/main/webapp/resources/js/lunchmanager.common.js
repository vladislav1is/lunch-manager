let form;

function makeEditable(datatableOpts) {
    ctx.datatableApi = $("#datatable").DataTable(
        //  https://api.jquery.com/jquery.extend/#jQuery-extend-deep-target-object1-objectN
        $.extend(true, datatableOpts,
            {
                "ajax": {
                    "url": ctx.ajaxUrl,
                    "dataSrc": ""
                },
                "retrieve": true,
                "paging": false,
                "info": true
            }
        ));
    form = $('#detailsForm');

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});

    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
}

function add() {
    $("#modalTitle").html(i18n["addTitle"]);
    form.find(":input").val("");
    let $select = $('#role');
    if ($select.length > 0) {
        $select.val("USER");
    }
    $("#editRow").modal();
}

function updateRow(id) {
    $("#modalTitle").html(i18n["editTitle"]);
    form.find(":input").val("");
    $.get(ctx.ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            if (Object.is("roles", key)) {
                let $select = $('#role');
                if (value.length > 1) {
                    $select.val("ADMIN");
                } else {
                    $select.val("USER");
                }
            } else {
                form.find("input[name='" + key + "']").val(value);
            }
        });
        $('#editRow').modal();
    });
}

function deleteRow(id) {
    if (confirm(i18n['common.confirm'])) {
        $.ajax({
            url: ctx.ajaxUrl + id,
            type: "DELETE"
        }).done(function () {
            ctx.updateTable();
            successNoty("common.deleted");
        });
    }
}

function updateTableByData(data) {
    ctx.datatableApi.clear().rows.add(data).draw();
}

function save() {
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        ctx.updateTable();
        successNoty("common.saved");
    });
}

let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(key) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + i18n[key],
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function renderEditBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='updateRow(" + row.id + ");'><span class='fa fa-pencil'></span></a>";
    }
}

function renderDeleteBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='deleteRow(" + row.id + ");'><span class='fa fa-remove'></span></a>";
    }
}

function renderEditMenuBtn(data, type, row) {
    if (type === "display") {
        return "<button type='button' onclick=\"window.location.href='restaurants/" + row.id + "/dishes/editor'\"\n" +
            " class='btn btn-sm btn-secondary'>" + i18n["common.edit"] + "</button>";
    }
}

function renderMenuBtn(data, type, row) {
    if (type === "display") {
        return "<button type='button' onclick=\"window.location.href='restaurants/" + row.id + "/dishes'\"\n" +
            " class='btn btn-sm btn-secondary'>" + i18n["common.view"] + "</button>";
    }
}

function renderVoteBtn(data, type, row) {
    if (type === "display") {
        return "<button type='button' onclick=\"window.location.href='restaurants/" + row.id + "/votes'\"\n" +
            " class='btn btn btn-secondary'>" +
            "<span class='fa text-light fa-plus'></span>" +
            "<span class='fa text-white-50 fa-users'></span>" +
            "</button>";
    }
}

function failNoty(jqXHR) {
    closeNoty();
    let errorInfo = jqXHR.responseJSON;
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + i18n["common.errorStatus"] + ": " + jqXHR.status +
            "<br>" + errorInfo.type + "<br>" + errorInfo.details.join("<br>"),
        type: "error",
        layout: "bottomRight"
    });
    failedNote.show()
}