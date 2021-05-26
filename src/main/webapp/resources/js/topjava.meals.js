const mealAjaxUrl = "ui/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl
};

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
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
                    "dsc"
                ]
            ]
        })
    );
});

function filter() {
    const filter = $("#filter");
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + "filter",
        data: filter.serialize()
    }).done(function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}

function updateTable() {
    filter();
}

function clearFilter() {
    $.get(ctx.ajaxUrl, function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}