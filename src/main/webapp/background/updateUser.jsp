<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/public.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/style.css">

<jsp:include page="${pageContext.request.contextPath}/background/common/header.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/background/common/sidebar.jsp"/>

<div class="admin_middle">
    <div class="modifyUserMain" style="margin-left: 488px">
        <form action="${pageContext.request.contextPath}/background/user.do" method="post" class="modifyUserForm">
            <input type="hidden" name="method" value="updateUser">
            <input type="hidden" name="userId" value="${user.userId}">
            <p>
                <span>昵&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</span>
                <input type="text" name="userName" value="${user.userName}">
                <span></span>
            </p>
            <p>
                <span>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span>
                <c:choose>
                    <c:when test="${user.gender=='1'}">
                        <input type="radio" name="gender" value="2" id="radio_boy">男
                        <input type="radio" name="gender" value="1" checked id="radio_girl">女
                    </c:when>
                    <c:otherwise>
                        <input type="radio" name="gender" value="2" checked id="radio_boy">男
                        <input type="radio" name="gender" value="1" id="radio_girl">女
                    </c:otherwise>
                </c:choose>
            </p>
            <p>
                <span>联系方式：</span>
                <input type="text" name="userPhone" value="${user.userPhone}">
                <span></span>
            </p>
            <p>
                <span>家庭住址：</span>
                <input type="text" name="userAddress" value="${user.userAddress}">
            </p>
            <p>
                <span>出生日期：</span>
                <input type="text" name="userBirthday" value="${birthday}" id="userBirthday">
                <span></span>
            </p>
            <p>
                <input type="button" value="保存" class="saveBtn" style="margin-left: 60px">
                <input type="button" value="返回" class="backBtn">
                <c:choose>
                    <c:when test="${msg == '更新成功'}">
                        <span style="margin-left: 15px; color: green">${msg}</span>
                    </c:when>
                    <c:otherwise>
                        <span style="margin-left: 15px; color: red">${msg}</span>
                    </c:otherwise>
                </c:choose>
            </p>
        </form>
    </div>
</div>

<jsp:include page="${pageContext.request.contextPath}/background/common/footer.jsp"/>

<script type="application/javascript" src="${pageContext.request.contextPath}/statics/js/common.js"></script>
<script type="application/javascript" src="${pageContext.request.contextPath}/statics/js/background/updateUser.js"></script>