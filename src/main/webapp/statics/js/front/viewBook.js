$(function () {
    $('.reserveBook').on('click', function () {
        let obj = $(this);
        if (obj.attr("bookStatus") === "2"){
            /*外借中，不可预约*/
            showTip()
        }else{
            /*在架*/
            window.location.href = path + "/front/book.do?method=toReserveBook&bookId=" + obj.attr("bookId");
        }
    })

    $('#yes').on('click', function () {
        cancelBtn();
    })

    $('.returnBook').on('click', function () {
        /*归还图书*/
    })

    $('.renewBook').on('click', function () {
        /*续借图书*/
    })
});

function showTip() {
    $('.zhezhao').css('display', 'block');
    $('.noBook').fadeIn();
}

function cancelBtn() {
    $('.zhezhao').css('display', 'none');
    $('.noBook').fadeOut();
}

$('.backBtn').on('click', function () {
    window.location.href = path + "/front/book.do?method=bookQuery";
})