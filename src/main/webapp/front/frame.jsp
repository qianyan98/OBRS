<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="${pageContext.request.contextPath}/front/common/header.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/front/common/navigation.jsp"/>

<div style="overflow: scroll; position: relative; height: calc(100vh - 180px)">
    <div class="middle" style="position: absolute; top: 10px; left: 275px">
        <div style="text-align: center">
            <p style="color: burlywood; font-size: 30px; font-style: oblique; padding-bottom: 10px">欢迎来到OBRS，在这里你可以找到属于你的世界</p>
            <img src="${pageContext.request.contextPath}/statics/images/frame.jpeg" title="书的世界" height="90%">
        </div>
    </div>
</div>

<jsp:include page="${pageContext.request.contextPath}/front/common/footer.jsp"/>