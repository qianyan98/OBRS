let reserveEndDate = null;

$(function () {
    reserveEndDate = $('#reserveEndDate');
    $('.renewBtn').on('click', function () {
        reserveEndDate.blur();
        if(reserveEndDate.attr("validateStatus") === "true"){
            $('.reserveForm').submit();
        }
    });

    reserveEndDate.on('focus', function () {
        validateTip(reserveEndDate.next(), {"color": "red"}, "预约时长最多一个月", false);
    }).on('blur', function () {
        let end = new Date(reserveEndDate.val()).getTime();

        if(isNaN(end)){
            validateTip(reserveEndDate.next(),  {"color": "red"}, "未选择时间", false);
            return;
        }
        // console.log(reserveEndDate.val());
        let now = new Date().getTime();
        let sub = Math.ceil((end - now) / (1000 * 60 * 60 * 24));
        // console.log(sub);
        if(sub > MAX_BORROW_TIME || sub <= 0){
            validateTip(reserveEndDate.next(), {"color": "red"}, "预约时长不正确，请重新选择", false);
        }else {
            validateTip(reserveEndDate.next(), {"color": "green"}, "符合要求", true);
        }
    });

    $('.backBtn').on('click', function () {
        window.location.href = path + "/front/book.do?method=reserveQuery";
    });
});

laydate.render({
    elem: '#reserveEndDate',
    done: function (value, date){
        reserveEndDate.blur();
    },
    showBottom: false
});
