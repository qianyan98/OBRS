<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>404错误</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/public.css">
</head>
<body>
    <div class="error404">
        <input type="button" class="backHome404" onclick="window.location.href = '${pageContext.request.contextPath}/login.jsp'" value="返回登录页面">
    </div>
</body>
</html>
