$(function () {
    $('.verifyCode').on('click', function () {
        /*加上时间戳是为了强制刷新，避免缓存*/
        $('.verifyCode').attr('src', '/verify.do?d=' + new Date().getTime());
    })
})