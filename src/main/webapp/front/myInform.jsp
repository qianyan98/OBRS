<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="${pageContext.request.contextPath}/front/common/header.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/front/common/navigation.jsp"/>

<div class="father">
    <jsp:include page="${pageContext.request.contextPath}/front/common/sider.jsp"/>

    <div class="middle">

        <%--填放个人信息--%>
        <div class="informMain">
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
        </div>

    </div>
</div>

<jsp:include page="${pageContext.request.contextPath}/front/common/footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/common.js"></script>
