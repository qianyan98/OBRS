<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="nav" style="overflow: scroll">
    <ul class="userAction">
        <li><a href="${pageContext.request.contextPath}/front/book.do?method=bookQuery">图书预约</a></li>
        <li><a href="${pageContext.request.contextPath}/front/book.do?method=reserveQuery">我的预约</a></li>
        <li><a href="${pageContext.request.contextPath}/front/user.do?method=viewUser">个人信息</a></li>
    </ul>
</div>
