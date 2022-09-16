let path = $('#path').val();    /*访问根路径*/
let referer = $('#referer').val();  /*访问历史路径*/
let MAX_BORROW_TIME = 31;
let pwdTable = ['1','2','3','4','5','6','7','8','9','0',
    'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','I','S','T','U','V','W','X','Y','Z',
    'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','i','s','t','u','v','w','x','y','z','_'];

function validateTip(element, css, tipString, status) {
    element.css(css);
    element.html(tipString);
    element.prev().attr("validateStatus", status);
}