$(function () {
    $('.backBtn').on('click', function () {
        window.location.href = path + "/background/user.do?method=userQuery";
    });
    $('.updateUser').on('click', function () {
        window.location.href = path + "/background/user.do?method=toUpdateUser&userId=" + $(this).attr("userId");
    })
})