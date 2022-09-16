<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/public.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/style.css">

<jsp:include page="${pageContext.request.contextPath}/background/common/header.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/background/common/sidebar.jsp"/>

<div class="admin_middle">
    <div class="informMain" style="margin-left: 550px">
        <p>
            <span>昵&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</span>
            <span>${user.userName}</span>
        </p>
        <p>
            <span>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span>
            <c:choose>
                <c:when test="${user.gender=='1'}"><span>女</span></c:when>
                <c:otherwise><span>男</span></c:otherwise>
            </c:choose>
        </p>
        <p>
            <span>联系方式：</span>
            <span>${user.userPhone}</span>
        </p>
        <p>
            <span>家庭住址：</span>
            <span>${user.userAddress}</span>
        </p>
        <p>
            <span>出生日期：</span>
            <span>${birthday}</span>
        </p>

        <p>
            <a class="updateUser" href="javascript:;" userId=${user.userId}><input type="button" class="updateBtn" value="更新" style="margin-left: 5px"></a>
            <input type="button" class="backBtn" value="返回" style="margin-left: 38px; margin-top: 30px">
            <span style="color: red">${msg}</span>
        </p>

    </div>
</div>

<jsp:include page="${pageContext.request.contextPath}/background/common/footer.jsp"/>

<script type="application/javascript" src="${pageContext.request.contextPath}/statics/js/common.js"></script>
<script type="application/javascript" src="${pageContext.request.contextPath}/statics/js/background/viewUser.js"></script>
