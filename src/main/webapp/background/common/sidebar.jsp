<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="adminSidebar">
    <ul class="adminActionList">
        <li><a href="${pageContext.request.contextPath}/background/book.do?method=bookQuery">书籍管理</a></li>
        <li><a href="${pageContext.request.contextPath}/background/book.do?method=getAllReserve">预约管理</a></li>
        <li><a href="${pageContext.request.contextPath}/background/user.do?method=userQuery">用户管理</a></li>
        <li><a href="${pageContext.request.contextPath}/background/changePwd.jsp">修改密码</a></li>
    </ul>
</div>