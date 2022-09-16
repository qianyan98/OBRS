let oldPwd = null;
let newPwd = null;
let confirmPwd = null;

$(function () {
    oldPwd = $('input[name="oldPwd"]');
    newPwd= $('input[name="newPwd"]');
    confirmPwd = $('input[name="confirmPwd"]');
    $('.adjustPwd').on('click', function () {
        let obj = $(this);
        if(obj.prev('input').attr('type') === 'password'){
            obj.prev('input').attr('type', 'text');
            obj.attr('src', path + '/statics/images/pwdHidden.png');
        }else{
            obj.prev('input').attr('type', 'password');
            obj.attr('src', path + '/statics/images/pwdShow.png');
        }
    });

    oldPwd.on('focus', function () {
        validateTip(oldPwd.next('img').next('span'), {"color": "red", "margin-left": "5px"},
            "请输入旧密码", false);
    }).on('blur', function () {
        /*发起请求，检查旧密码正确性*/
        let pwd = oldPwd.val();
        $.ajax({
            type: 'GET',
            url: path + '/background/user.do?method=checkOldPwd',
            data: {"oldPwd": pwd},
            success: function (data) {
                if(data === '密码正确'){
                    validateTip(oldPwd.next('img').next('span'), {"color": "green", "margin-left": '5px'},
                        "密码正确", true);
                }else{
                    validateTip(oldPwd.next('img').next('span'), {"color": "red", "margin-left": '5px'},
                        "密码错误，请重新输入", false);
                }
            }
        });
    });
    newPwd.on('focus', function () {
        validateTip(newPwd.next('img').next('span'), {"color": "red", "margin-left": '5px'},
            "新密码长度不超过8", false);
    }).on('blur', function () {
        let pwd = newPwd.val();
        if(pwd.length > 8){
            validateTip(newPwd.next('img').next('span'), {"color": "red", "margin-left": '5px'},
                "新密码长度不超过8", false);
        }else if(pwd.length === 0){
            validateTip(newPwd.next('img').next('span'), {"color": "red", "margin-left": '5px'},
                "请输入新密码", false);
        }else{
            validateTip(newPwd.next('img').next('span'), {"color": "green", "margin-left": '5px'},
                "新密码符合要求", true);
        }
    });
    confirmPwd.on('focus', function () {
        validateTip(confirmPwd.next('img').next('span'), {"color": "red", "margin-left": '5px'},
            "请输入确认密码", false);
    }).on('blur', function () {
        let pwd1 = newPwd.val();
        let pwd2 = confirmPwd.val();
        if(pwd2.length === 0){
            validateTip(confirmPwd.next('img').next('span'), {"color": "red", "margin-left": '5px'},
                "请输入确认密码", false);
        }else if(pwd2 !== pwd1){
            validateTip(confirmPwd.next('img').next('span'), {"color": "red", "margin-left": '5px'},
                "确认密码与新密码不一致", false);
        }else{
            validateTip(confirmPwd.next('img').next('span'), {"color": "green", "margin-left": '5px'},
                "确认密码符合要求", true);
        }
    });
    $('.changePwdBtn').on('click', function () {
        if(oldPwd.next('img').attr("validateStatus") === "true"
            && newPwd.next('img').attr("validateStatus") === "true"
            && confirmPwd.next('img').attr("validateStatus") === "true"){
            /*打开子窗口*/
            openDlg();
        }
    });
    $('.OkBtn').on('click', function () {
        $('.changePwdForm').submit();
    });
    $('.cancelBtn').on('click', function () {
        closeDlg();
    });
})

function openDlg() {
    $('.zhezhao').css("display", "block");
    $('.changePwdDlg').fadeIn();
}

function closeDlg() {
    $('.zhezhao').css("display", "none");
    $('.changePwdDlg').fadeOut();
}