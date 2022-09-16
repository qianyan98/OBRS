let userObj = null;

$(function () {
    $('.deleteUser').on('click', function () {
        userObj = $(this);
        changeDlgContent("是否确定删除该用户？", 1);
        openDlg();
    });
    $('.cancelBtn').on('click', function () {
        closeDlg();
    });
    $('.OkBtn').on('click', function () {
        deleteUser(userObj.attr("userId"), userObj.attr("userRole"));
    });
    $('.viewUser').on('click', function () {
        userObj = $(this);
        window.location.href = path + "/background/user.do?method=viewUser&userId=" + userObj.attr("userId");
    })
});

function deleteUser(userId, userRole) {
    if(userRole === "管理员"){
        changeDlgContent("管理员不可删除", 0);
    }else{
        $.ajax({
            type: "GET",
            url: path + "/background/user.do",
            data: {"userId": userId, "method": "deleteUser"},
            success: function (data) {
                if(data === "删除成功"){
                    userObj.parents('tr').remove();
                    closeDlg();
                }else{
                    changeDlgContent("删除失败", 0);
                }
            }
        })
    }
}

function openDlg(){
    $('.zhezhao').css("display", "block");
    $('.deleteUserDlg').fadeIn();
}

function closeDlg() {
    $('.zhezhao').css("display", "none");
    $('.deleteUserDlg').fadeOut();
}

function changeDlgContent(content, action) {
    let target = $('.deleteUserDlgContent');
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
                let userId = userObj.attr("userId");
                let userRole = userObj.attr("userRole");
                deleteUser(userId, userRole);
            });
        }
    }
}
