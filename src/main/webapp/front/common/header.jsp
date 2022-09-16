<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>OBRS</title>
    <link rel="icon" href="${pageContext.request.contextPath}/statics/images/favicon.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/statics/css/public.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/statics/css/style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/laydate/laydate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/jquery-3.6.0.js"></script>
</head>
<body>
<div class="head">
    <div class="head-container">
        <a href="${pageContext.request.contextPath}/front/frame.jsp">
            <span class="logo">OBRS</span>
        </a>
        <span class="welcomeTip">${sessionScope.USER_SESSION.userName}, 欢迎进入OBRS</span>
        <div class="logoutBtn">
            <button type="button" name="logout"><a href="${pageContext.request.contextPath}/front/logout.do">注销</a></button>
        </div>
    </div>
</div>

<input type="hidden" name="path" id="path" value="${pageContext.request.contextPath}">
<input type="hidden" name="referer" id="referer" value="<%=request.getHeader("Referer")%>">
