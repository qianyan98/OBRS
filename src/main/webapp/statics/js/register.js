let username = null;
let password = null;
let email = null;
let sendVerifyCode = null;
let countTime = 60;
let id;

$(function () {
    username = $('.registerForm p input[name="username"]');
    password = $('.registerForm p input[name="password"]');
    email = $('.registerForm p input[name="email"]');
    sendVerifyCode = $('.sendVerifyCode');

    username.on('focus', function () {
        validateTip(username.next('span'), {"color": 'red', "margin-left": "10px", "font-size": "15px"}, "用户名长度不超过10", false);
    }).on('blur', function () {
        let name = username.val();
        if(name.length > 10){
            validateTip(username.next('span'), {"color": 'red', "margin-left": "10px", "font-size": "15px"}, "用户名长度超过10", false);
        }else if(name.length === 0){
            validateTip(username.next('span'), {"color": 'red', "margin-left": "10px", "font-size": "15px"}, "请输入用户名", false);
        }else{
            $.ajax({
                url: "/register.do",
                data: {"method": "isNameExist", "userName": name},
                success: function (data) {
                    if(data === "存在"){
                        validateTip(username.next('span'), {"color": 'red', "margin-left": "10px", "font-size": "15px"}, "用户名已被占用", false);
                    }else{
                        validateTip(username.next('span'), {"color": 'green', "margin-left": "10px", "font-size": "15px"}, "用户名可用", true);
                    }
                }
            })
        }
    });
    password.on('focus', function () {
        validateTip(password.next('span'), {"color": 'red', "margin-left": "10px", "font-size": "15px"}, "数字、下划线、英文", false);
    }).on('blur', function () {
        let pwd = password.val();
        if(pwd.length === 0){
            validateTip(password.next('span'), {"color": 'red', "margin-left": "10px", "font-size": "15px"}, "请输入密码", false);
        }else{
            if(checkPwd(pwd) === true){
                validateTip(password.next('span'), {"color": 'green', "margin-left": "10px", "font-size": "15px"}, "密码符合要求", true);
            }else{
                validateTip(password.next('span'), {"color": 'red', "margin-left": "10px", "font-size": "15px"}, "密码包含非法字符", false);
            }
        }
    });
    email.on('focus', function () {
        validateTip(email.next('input').next('span'), {"color": 'red', "margin-left": "10px", "font-size": "15px"}, "请输入邮箱", false);
    }).on('blur', function () {
        let mail = email.val();
        if(mail.length === 0){
            validateTip(email.next('input').next('span'), {"color": 'red', "margin-left": "10px", "font-size": "15px"}, "请输入邮箱", false);
        }else{
            if(checkEmail(mail)){
                validateTip(email.next('input').next('span'), {"color": 'green', "margin-left": "10px", "font-size": "15px"}, "邮箱格式正确", true);
            }else{
                validateTip(email.next('input').next('span'), {"color": 'red', "margin-left": "10px", "font-size": "15px"}, "邮箱格式错误", false);
            }
        }
    });
    sendVerifyCode.on('click', function () {
        if(email.next('input').attr("validateStatus") === "true"){
            $.ajax({
                url: "/mail.do",
                data: {"method": "sendVerifyCode", "email": email.val()},
                success: function (data) {
                    disableBtn(sendVerifyCode);
                }
            })
            // disableBtn(sendVerifyCode);
        }else{
            validateTip(email.next('input').next('span'), {"color": 'red', "margin-left": "10px", "font-size": "15px"}, "请输入邮箱", false);
        }
    });
    $('.registerBtn input[type="submit"]').on('click', function () {
        if(username.attr("validateStatus") === "true"
            && password.attr("validateStatus")
            && email.next('input').attr("validateStatus") && sendVerifyCode.val().length !== 0){
            $('.registerForm').submit();
        }
    })
});

function checkPwd(pwd) {
    for(let i = 0; i < pwd.length; i++){
        if(pwdTable.includes(pwd[i]) === false){
            return false;
        }
    }
    return true;
}

function checkEmail(email) {
    let reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/
    return reg.test(email);
}

function disableBtn(element) {
    if(countTime === 0){
        element.removeAttr("disabled");
        element.attr("value", "获取验证码");
        countTime = 60;
        clearTimeout(id);
    }else{
        element.attr("disabled", "true");
        element.attr("value", "重新发送(" + countTime + ")");
        countTime--;
        id = setTimeout(function () {
            disableBtn(element);
        }, 1000);
    }
}