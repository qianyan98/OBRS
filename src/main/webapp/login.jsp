<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>OBRS</title>
    <link rel="icon" href="${pageContext.request.contextPath}/statics/images/favicon.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/statics/css/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/statics/css/style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/login.js"></script>
</head>
<body>
    <div style="width: 1440px">
        <div class="left">

        </div>

        <div class="right">
            <p class="loginTip">OBRS欢迎您的到来</p>
            <form action="${pageContext.request.contextPath}/login.do" method="post" class="loginForm">
                <p style="font-size: 15px; color: red">${msg}</p>
                <p>
                    <span>用户名：</span>
                    <input type="text" name="username">
                </p>
                <p>
                    <span>密&nbsp;&nbsp;&nbsp;&nbsp;码：</span>
                    <input type="password" name="password">
                </p>
                <p>
                    <span>验证码：</span>
                    <input type="text" name="verificationCode">
                    <img class="verifyCode" src="${pageContext.request.contextPath}/verify.do"/>
                </p>
                <p class="loginBtn">
                    <input type="submit" value="登录">
                    <input type="reset" value="重置">
                </p>
                <p>
                    <a href="${pageContext.request.contextPath}/register.jsp" style="color: red; font-size: 15px; margin-left: 60px">没有账户？前往注册</a>
                </p>
            </form>
        </div>
        <div class="clear"></div>
    </div>
</body>
</html>
