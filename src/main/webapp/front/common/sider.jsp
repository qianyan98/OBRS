<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="sideBar">
    <ul class="sideBarContent">
        <li><a href="${pageContext.request.contextPath}/front/user.do?method=viewUser">个人信息</a></li>
        <li><a href="${pageContext.request.contextPath}/front/user.do?method=toModifyUser">信息修改</a></li>
        <li><a href="${pageContext.request.contextPath}/front/changePwd.jsp">密码修改</a></li>
    </ul>
</div>
