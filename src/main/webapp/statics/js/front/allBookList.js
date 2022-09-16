$(function () {
    $('.viewBook').on('click', function () {
        let obj = $(this);
        window.location.href = path + "/front/book.do?method=viewBook&bookId=" + obj.attr("bookId");
    })

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
})

function showTip() {
    $('.zhezhao').css('display', 'block');
    $('.noBook').fadeIn();
}

function cancelBtn() {
    $('.zhezhao').css('display', 'none');
    $('.noBook').fadeOut();
}


laydate.render({
    elem: '.selectTime'
    ,range: ['#startDate', '#endDate']
});
