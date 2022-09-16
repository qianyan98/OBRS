$(function () {
    $('input[name="userName"]').on('focus', function () {
        $.ajax({
            url: path + "/background/user.do",
            data: {"method": "getLikeUserName", "userName": $('input[name="userName"]').val()},
            success: function (data) {
                showLikeName(data);
            }
        })
    }).on('blur', function () {
        setTimeout(()=>{
            $('.likeName').css('display', 'none');
            $('.likeNameList li').remove();
        }, 200);
    }).on('input propertychange', function () {
        $('.likeName').css('display', 'none');
        $('.likeNameList li').remove();
        $.ajax({
            url: path + "/background/user.do",
            data: {"method": "getLikeUserName", "userName": $('input[name="userName"]').val()},
            success: function (data) {
                showLikeName(data);
            }
        })
    });
    $('.renewReserve').on('click', function () {
        /*续借*/
        let obj = $(this);

        let renewDlg = $('.renewDlg');
        let renewDlg_p = $('.renewDlg p');
        if(obj.attr("reserveStatus") === "1"){
            renewDlg_p.html("未在续借日期范围内");
            renewDlg_p.css("color", "red");
            openDeleteDlg(renewDlg);
        } else{
            window.location.href = path + "/background/book.do?method=toRenewBook&bookId="
                + obj.attr("bookId") + "&reserveId=" + obj.attr("reserveId");
        }
    });
    $('.renewDlgOkBtn').on('click', function (){
        cancel($('.renewDlg'));
    });
    $('.notification').on('click', function () {
        window.location.href = path + "/background/book.do?method=toNotification&reserveId=" + $(this).attr("reserveId");
    })
})


function showLikeName(nameList) {
    //显示
    $('.likeName').css('display', 'block');
    nameList = JSON.parse(nameList);
    //插入
    let likeNameList = $('.likeNameList');
    let child = "";
    for (let i = 0; i < nameList.length; i++) {
        child += ("<li style='list-style: none'>" + nameList[i]['userName'] + "</li>");
    }
    likeNameList.append(child);
    $('li').on('click', function () {
        let obj = $(this);
        $('#reserveUserName input[name="userName"]').val(obj[0]['innerText']);
    })
}

function openDeleteDlg(element) {
    $('.zhezhao').css("display", "block");
    element.fadeIn();
}

function cancel(element) {
    $('.zhezhao').css("display", "none");
    element.fadeOut();
}