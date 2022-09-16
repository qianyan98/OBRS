laydate.render({
    elem: '.selectTime'
    ,range: ['#startDate', '#endDate']
});

let bookObj;

$(function () {
    $('.deleteBook').on('click', function () {
        bookObj = $(this);
        changeDlgContent("是否确定删除该书？", 1);
        openDlg();
    });
    $('.cancelBtn').on('click', function () {
        closeDlg();
    });
    $('.OkBtn').on('click', function () {
        deleteBook(bookObj.attr("bookId"), bookObj.attr("bookStatus"));
    });
    $('.viewBook').on('click', function () {
        bookObj = $(this);
        window.location.href = path + "/background/book.do?method=viewBook&bookId=" + bookObj.attr("bookId");
    });
    $('.search input[value="添加图书"]').on('click', function () {
        window.location.href = path + "/background/addBook.jsp";
    })
})

function deleteBook(bookId, bookStatus) {
    if(bookStatus === '2'){
        changeDlgContent("书籍外借中，暂不可删除", 0);
    }else{
        $.ajax({
            url: path + "/background/book.do?method=deleteBook",
            data: {"bookId": bookId},
            success: function (data) {
                if(data === "删除成功"){
                    bookObj.parents('tr').remove();
                    closeDlg();
                }else{
                    changeDlgContent(data, 0);
                }
            }
        })
    }
}

function openDlg() {
    $('.zhezhao').css('display', 'block');
    $('.deleteBookDlg').fadeIn();
}

function closeDlg(){
    $('.zhezhao').css('display', 'none');
    $('.deleteBookDlg').fadeOut();
}

function changeDlgContent(content, action) {
    let target = $('.deleteBookDlgContent');
    target.text(content);
    if(action === 0){
        $('.OkBtn').remove();
        $('.cancelBtn').html("确定");
    }else{
        let cancelBtn = $('.cancelBtn');
        cancelBtn.html("取消");
        if($('.OkBtn').length === 0){
            cancelBtn.after("<button class=\"OkBtn\">确认</button>");
            let OkBtn = $('.OkBtn');
            OkBtn.css({"margin": "20px 10px", "width": "40px", "height": "30px"});
            OkBtn.on('click', function () {
                let bookId = bookObj.attr("bookId");
                let bookStatus = bookObj.attr("bookStatus");
                deleteBook(bookId, bookStatus);
            });
        }
    }
}