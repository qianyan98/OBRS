<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>OBRS</title>
    <link rel="icon" href="${pageContext.request.contextPath}/statics/images/favicon.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/statics/css/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/statics/css/style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/common.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/register.js"></script>
</head>
<body>
<div style="width: 1440px">
    <div class="left">

    </div>

    <div class="right">
        <p class="loginTip">OBRS欢迎您的到来</p>
        <form action="${pageContext.request.contextPath}/register.do" method="post" class="registerForm">
            <input type="hidden" name="method" value="register">
            <c:choose>
                <c:when test="${msg == '注册成功'}">
                    <p style="font-size: 15px; color: green">${msg}</p>
                </c:when>
                <c:otherwise>
                    <p style="font-size: 15px; color: red">${msg}</p>
                </c:otherwise>
            </c:choose>
            <p>
                <span>用户名：</span>
                <input type="text" name="username" value="${username}">
                <span></span>
            </p>
            <p>
                <span>密&nbsp;&nbsp;&nbsp;&nbsp;码：</span>
                <input type="password" name="password" value="${password}">
                <span></span>
            </p>
            <p>
                <span>邮&nbsp;&nbsp;&nbsp;&nbsp;箱：</span>
                <input type="text" name="email" value="${email}">
                <input type="button" name="sendVerifyCode" class="sendVerifyCode" value="获取验证码" style="margin-left: 5px; border: none; background: white">
                <span></span>
            </p>
            <p>
                <span>验证码：</span>
                <input type="text" name="verificationCode" value="${verificationCode}">
<%--                <img class="verifyCode" src="${pageContext.request.contextPath}/verify.do"/>--%>
            </p>
            <p class="registerBtn">
                <input type="submit" value="注册">
                <input type="reset" value="重置">
            </p>
            <p>
                <a href="${pageContext.request.contextPath}/login.jsp" style="color: red; font-size: 15px; margin-left: 60px">已有账户？前往登录</a>
            </p>
        </form>
    </div>
    <div class="clear"></div>
</div>
</body>
</html>
