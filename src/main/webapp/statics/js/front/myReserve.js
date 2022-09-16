laydate.render({
    elem: '.selectTime'
    ,range: ['#startDate', '#endDate']
});

let bookObj;

function deleteReserve(reserveId, bookId) {
    $.ajax({
        url: path + "/front/book.do",
        data: {"method": "deleteReserve", "bookId": bookId, "reserveId": reserveId},
        success: function (data) {
            if(data === "删除失败"){
                changeDlgContent("删除失败!", 1);
            }else{
                /*删除表中的行*/
                bookObj.parents("tr").remove();
                cancel($('.deleteDlg'));
            }
        }
    });
}

$(function () {
    $('.returnBook').on('click', function () {
        /*打开删除对话框*/
        bookObj = $(this);
        changeDlgContent("是否确认归还该书？", 0)
        openDeleteDlg($('.deleteDlg'));
    });
    $('.OkBtn').on('click', function () {
        let bookId = bookObj.attr("bookId");
        let reserveId = bookObj.attr("reserveId");
        deleteReserve(reserveId, bookId);
    });
    $('.cancelBtn').on('click', function () {
        cancel($('.deleteDlg'));
    });
    $('.renewBook').on('click', function () {
        /*续借*/
        let obj = $(this);

        let renewDlg = $('.renewDlg');
        let renewDlg_p = $('.renewDlg p');
        if(obj.attr("count") === "2"){
            renewDlg_p.html("续借次数已用完，请在逾期前归还该书");
            renewDlg_p.css("color", "red");
            openDeleteDlg(renewDlg);
        } else if(obj.attr("reserveStatus") === "1"){
            renewDlg_p.html("未在续借日期范围内");
            renewDlg_p.css("color", "red");
            openDeleteDlg(renewDlg);
        } else{
            window.location.href = path + "/front/book.do?method=toRenewBook&bookId="
                + obj.attr("bookId") + "&reserveId=" + obj.attr("reserveId");
        }
    });
    $('.renewDlgOkBtn').on('click', function (){
        cancel($('.renewDlg'));
    })
})

function openDeleteDlg(element) {
    $('.zhezhao').css("display", "block");
    element.fadeIn();
}

function cancel(element) {
    $('.zhezhao').css("display", "none");
    element.fadeOut();
}

function changeDlgContent(content, action) {
    let deleteDlgMain = $('.deleteDlgMain p');
    deleteDlgMain.html(content);
    if(action === 1){
        deleteDlgMain.css("color", "red");
        $('.deleteDlgMain .OkBtn').remove();
    }else{
        if($(".deleteDlgMain .OkBtn").length === 0){
            $('.deleteDlgMain .cancelBtn').after("<button class=\"OkBtn\">确认</button>");
            let okBtn = $('.OkBtn');
            okBtn.css({"margin": "20px 10px", "width": "40px", "height": "30px"})
            okBtn.on('click', function () {
                let bookId = bookObj.attr("bookId");
                let reserveId = bookObj.attr("reserveId");
                deleteReserve(reserveId, bookId);
            });
        }
    }
}