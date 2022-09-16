$(function () {
    $('.backBtn').on('click', function () {
        window.location.href = path + "/background/book.do?method=bookQuery";
    });
    $('.updateBook').on('click', function () {
        window.location.href = path + "/background/book.do?method=toUpdateBook&bookId=" + $(this).attr("bookId");
    })
})