<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/public.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/style.css">

<jsp:include page="${pageContext.request.contextPath}/background/common/header.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/background/common/sidebar.jsp"/>

<div class="admin_middle">
    <div style="margin-left: 470px;margin-top: 100px">
        <form action="${pageContext.request.contextPath}/background/book.do" method="post" class="reserveForm">
            <input type="hidden" name="method" value="renewBook">
            <input type="hidden" name="reserveId" value="${reserveId}">
            <input type="hidden" name="bookId" value="${book.bookId}">
            <p><span>书&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：<input type="text" readonly value="${book.bookName}"></span></p>
            <p><span>作&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;者：<input type="text" readonly value="${book.bookAuthor}"></span></p>
            <p><span>类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：<input type="text" readonly value="${book.bookType}"></span></p>
            <p><span>出版时间：<input type="text" readonly value="${book.bookPublishDate}"></span></p>
            <%--预约开始时间：默认为是续借操作发生的日期--%>
            <input type="hidden" name="startDate" value="${startDate}">
            <%--选择预约结束时间--%>
            <p>
                <span>续借时间：<input type="text" autocomplete="off" id="reserveEndDate" placeholder="续借结束日期" name="reserveEndDate" value="${reserveEndDate}"><span style="margin-left: 10px"></span></span>
            </p>
            <p>
                <c:choose>
                    <c:when test="${msg == '续借成功'}">
                        <span class="reserveTip" style="color: green">${msg}</span>
                    </c:when>
                    <c:otherwise>
                        <span class="reserveTip" style="color: red">${msg}</span>
                    </c:otherwise>
                </c:choose>
            </p>
            <p>
                <input type="button" value="续借" class="renewBtn">
                <input type="button" value="返回" class="backBtn">
            </p>
        </form>
    </div>
</div>

<jsp:include page="${pageContext.request.contextPath}/background/common/footer.jsp"/>

<script type="application/javascript" src="${pageContext.request.contextPath}/statics/js/common.js"></script>
<script type="application/javascript" src="${pageContext.request.contextPath}/statics/js/background/renewReserve.js"></script>

