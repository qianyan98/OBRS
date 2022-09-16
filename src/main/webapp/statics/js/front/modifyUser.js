let userName = null;
let userPhone = null;
let userBirthDay = null;

$(function () {
    userName = $('input[name="userName"]');
    userPhone = $('input[name="userPhone"]');
    userBirthDay = $('input[name="userBirthday"]');

    let name = userName.val();
    let phone = userPhone.val();
    let birthday = userBirthDay.val();

    let name1 = userName.val();
    let phone1 = userPhone.val();
    let birthday1 = userBirthDay.val();

    userName.on('focus', function () {
        validateTip(userName.next(), {"color": "red", "margin-left": "10px"}, "昵称长度不超过10", false);
    }).on('blur', function () {
        name1 = userName.val();
        if(name1.length > 10){
            validateTip(userName.next(), {"color": "red", "margin-left": "10px"}, "昵称长度超过10，请重新输入", false);
        }else if(name1.length === 0){
            validateTip(userName.next(), {"color": "red", "margin-left": "10px"}, "请输入昵称", false);
        }else{
            /*发起请求，判断用户名是否重复*/
            $.ajax({
               url: "/front/user.do",
               data: {"method": "isUserNameExist", "userName": name1},
               success: function (data) {
                   // console.log(name1);
                   // console.log(name);
                   if(data === "存在" && name1 !== name){
                       validateTip(userName.next(), {"color": "red", "margin-left": "10px"}, "昵称已被占用", false);
                   }else if(data === "其他错误"){
                       validateTip(userName.next(), {"color": "red", "margin-left": "10px"}, "网络错误，请重试", false);
                   }else{
                       validateTip(userName.next(), {"color": "green", "margin-left": "10px"}, "昵称符合要求", true);
                   }
               }
            });
        }
    });
    userPhone.on('focus', function () {
        validateTip(userPhone.next(), {"color": "red", "margin-left": "10px"}, "请输入正确的号码格式", false);
    }).on('blur', function () {
        phone1 = userPhone.val();
        if(phone1.length !== 11 || !['138', '135', '182', '139'].includes(phone1.substr(0, 3))){
            validateTip(userPhone.next(), {"color": "red", "margin-left": "10px"}, "请输入正确的号码格式", false);
        }else{
            validateTip(userPhone.next(), {"color": "green", "margin-left": "10px"}, "号码符合要求", true);
        }
    });
    userBirthDay.on('focus', function () {
        validateTip(userBirthDay.next(), {}, "", false);
    }).on('blur', function () {
        birthday1 = new Date(userBirthDay.val());
        let now = new Date()
        // console.log(now);
        if(birthday1 >= now){
            validateTip(userBirthDay.next(), {"color": "red", "margin-left": "10px"}, "请选择正确的出生日期", false);
        }else{
            validateTip(userBirthDay.next(), {"color": "green", "margin-left": "10px"}, "出生日期符合要求", true);
        }
    });
    $('.saveBtn').on('click', function () {
        // console.log("1111111");
        // console.log(name === name1);
        // console.log(userName.attr("validateStatus") === "true");
        // console.log(phone === phone1);
        // console.log(userPhone.attr("validateStatus") === "true");
        // console.log(birthday === birthday1);
        // console.log(userBirthDay.attr("validateStatus") === "true");
        if((name === name1 || userName.attr("validateStatus") === "true")
            && (phone === phone1 || userPhone.attr("validateStatus") === "true")
            && (birthday === birthday1 || userBirthDay.attr("validateStatus") === "true")){
            $('.modifyUserForm').submit();
        }
    })
});


laydate.render({
    elem: '#userBirthday',
    done: function (data, value) {
        userBirthDay.blur();
    },
    showBottom: false
});