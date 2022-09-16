let doc = null;
let docContent = null;

let emailTitle = null;
let sendTo = null;

let email = null;
let email1 = null;

$(function () {
    doc = $('#textBox');
    docContent = doc.html();
    emailTitle = $('#emailTitle');
    sendTo = $('#sendTo');

    email1 = sendTo.val();
    email = sendTo.val();

    emailTitle.on('focus', function () {
        validateTip(emailTitle.next(), {"color": "red", "margin-left": "10px"}, "请输入邮件主题", false);
    }).on('blur', function () {
        let subject = emailTitle.val();
        if(subject.length === 0){
            validateTip(emailTitle.next(), {"color": "red", "margin-left": "10px"}, "请输入邮件主题", false);
        }else{
            validateTip(emailTitle.next(), {"color": "green", "margin-left": "10px"}, "符合要求", true);
        }
    });

    sendTo.on('focus', function () {
        validateTip(sendTo.next(), {"color": "red", "margin-left": "10px"}, "请输入接收人邮箱", false);
    }).on('blur', function () {
        email = sendTo.val();
        if(email.length === 0){
            validateTip(sendTo.next(), {"color": "red", "margin-left": "10px"}, "请输入接收人邮箱", false);
        }else{
            //检查邮箱
            if(checkEmail(email)){
                validateTip(sendTo.next(), {"color": "green", "margin-left": "10px"}, "符合要求", true);
            }else{
                validateTip(sendTo.next(), {"color": "red", "margin-left": "10px"}, "邮箱格式不正确", false);
            }
        }
    });

    $('#sendEmailBtn').on('click', function () {
        console.log(emailTitle.attr('validateStatus'));
        console.log(sendTo.attr('validateStatus'));
        console.log(email1 === email);
        console.log(doc.html());
        if(emailTitle.attr('validateStatus') === "true" && (email1 === email || sendTo.attr('validateStatus') === "true") && doc.html().length > 0){
            //发起请求
            $.ajax({
                type: "POST",
                url: path + "/mail.do",
                data: {"method": "sendNotification", "sendTo": sendTo.val(), "sendFrom": $('.sendFrom').val(), "subject": emailTitle.val(), "content": doc.html()},
                success: function (data) {
                    let outcome = $('#sendOutcome');
                    if(data === "发送成功"){
                        outcome.html('发送成功');
                        outcome.css('color', 'green');
                    }else{
                        outcome.html('发送失败');
                        outcome.css('color', 'red');
                    }
                },
                error: function () {
                    let outcome = $('#sendOutcome');
                    outcome.html('发送失败');
                    outcome.css('color', 'red');
                }
            })
        }
    })
})

function formatDoc(cmd, value) {
    document.execCommand(cmd, false, value);
}

function printDoc() {
    let oPrintWin = window.open("","_blank","width=450,height=470,left=400,top=100,menubar=yes,toolbar=no,location=no,scrollbars=yes");
    oPrintWin.document.open();
    oPrintWin.document.write("<!doctype html><html><head><title>Print<\/title><\/head><body onload=\"print();\">" + doc.innerHTML + "<\/body><\/html>");
    oPrintWin.document.close();
}

function checkEmail(email) {
    let reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/
    return reg.test(email);
}