let bookName = null;
let bookAuthor = null;
let bookType = null;
let bookPublishDate = null;
let bookDescription = null;

$(function () {
    bookName = $('input[name="bookName"]');
    bookAuthor = $('input[name="bookAuthor"]');
    bookType = $('input[name="bookType"]');
    bookPublishDate = $('input[name="bookPublishDate"]');
    bookDescription = $('textarea[name="bookDescription"]');
    let name = bookName.val();
    let author = bookAuthor.val();
    let type = bookType.val();
    let publishDate = bookPublishDate.val();
    let description = bookDescription.val();

    $('.backBtn').click(function () {
        window.location.href = path + "/background/book.do?method=bookQuery";
    });
    bookName.on('focus', function () {
        validateTip(bookName.next(), {"color":"red", "margin-left": "10px"}, "请输入书籍名称", false);
    }).on('blur', function () {
        name = bookName.val();
        if(name.length === 0){
            validateTip(bookName.next(), {"color":"red", "margin-left": "10px"}, "书籍名称不可为空", false);
        }else{
            validateTip(bookName.next(), {"color":"green", "margin-left": "10px"}, "符合要求", true);
        }
    });
    bookAuthor.on('focus', function () {
        validateTip(bookAuthor.next(), {"color":"red", "margin-left": "10px"}, "请输入作者名称", false);
    }).on('blur', function () {
        author = bookAuthor.val();
        if(author.length === 0){
            validateTip(bookAuthor.next(), {"color":"red", "margin-left": "10px"}, "作者名称不可为空", false);
        }else{
            validateTip(bookAuthor.next(), {"color":"green", "margin-left": "10px"}, "符合要求", true);
        }
    });
    bookType.on('focus', function () {
        validateTip(bookType.next(), {"color":"red", "margin-left": "10px"}, "请输入书籍类型", false);
    }).on('blur', function () {
        type = bookType.val();
        if(type.length === 0){
            validateTip(bookType.next(), {"color":"red", "margin-left": "10px"}, "书籍类型不可为空", false);
        }else{
            validateTip(bookType.next(), {"color":"green", "margin-left": "10px"}, "符合要求", true);
        }
    });
    bookPublishDate.on('focus', function () {
        validateTip(bookPublishDate.next(), {"color":"red", "margin-left": "10px"}, "请输入出版时间", false);
    }).on('blur', function () {
        publishDate = bookPublishDate.val();
        if(publishDate.length === 0){
            validateTip(bookPublishDate.next(), {"color":"red", "margin-left": "10px"}, "出版时间不可为空", false);
        }else{
            let inputDate = new Date(publishDate);
            let now = new Date();
            if(inputDate >= now){
                validateTip(bookPublishDate.next(), {"color":"red", "margin-left": "10px"}, "出版时间不能超过当前日期", false);
            }else{
                validateTip(bookPublishDate.next(), {"color":"green", "margin-left": "10px"}, "符合要求", true);
            }

        }
    });
    bookDescription.on('focus', function () {
        validateTip(bookDescription.next(), {"color":"red", "margin-left": "10px"}, "请输入书籍简介", false);
    }).on('blur', function () {
        description = bookDescription.val();
        if(description.length === 0){
            validateTip(bookDescription.next(), {"color":"red", "margin-left": "10px"}, "书籍简介不可为空", false);
        }else{
            validateTip(bookDescription.next(), {"color":"green", "margin-left": "10px"}, "符合要求", true);
        }
    });
    $('.addBookBtn').on('click', function () {
        if((bookName.attr("validateStatus") === "true")
            && (bookAuthor.attr("validateStatus") === "true")
            && (bookType.attr("validateStatus") === "true")
            && (bookPublishDate.attr("validateStatus") === "true")
            && (bookDescription.attr("validateStatus") === "true")){
            $('.addBookForm').submit();
        }
    });
});

laydate.render({
    elem: '#publishDate',
    done: function (data, value) {
        bookPublishDate.blur();
    },
    showBottom: false
});
