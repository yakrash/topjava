const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

// $(document).ready(function () {
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
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
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

function updateTable() {
    showTable();
}

function changeStatus(id) {
    let checkbox = document.getElementById("status" + id);
    let status = checkbox.checked;
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + "change-status/" + id,
        data: {status: status}
    }).done(function () {
        checkbox.closest('tr').setAttribute("data-userStatus", status);
        // successNoty("User " + checkbox.closest('tr').getAttribute("data-userStatus"));
        successNoty("User " + (status ? "enabled" : "disabled"))
    });
}
