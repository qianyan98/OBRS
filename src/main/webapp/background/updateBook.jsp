<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/public.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/style.css">

<jsp:include page="${pageContext.request.contextPath}/background/common/header.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/background/common/sidebar.jsp"/>

<div class="admin_middle">
    <div class="bookInfo_admin">
        <form action="${pageContext.request.contextPath}/background/book.do?method=updateBook" method="post" class="updateBookForm">
            <input type="hidden" name="bookId" value="${book.bookId}">
            <p>
                <span>书&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span>
                <input type="text" name="bookName" value="${book.bookName}" style="width: 400px;">
                <span></span>
            </p>
            <p>
                <span>作&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;者：</span>
                <input type="text" name="bookAuthor" value="${book.bookAuthor}" style="width: 400px;">
                <span></span>
            </p>
            <p>
                <span>类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：</span>
                <input type="text" name="bookType" value="${book.bookType}" contenteditable="true" style="width: 400px">
                <span></span>
            </p>
            <p>
                <span>出版日期：</span>
                <input type="text" name="bookPublishDate" id="publishDate" value="${bookPublishDate}" style="width: 400px;">
                <span></span>
            </p>
            <p>
                <span class="bookIntroTip">简&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;介：</span>
                <textarea name="bookDescription" class="bookIntro" cols="50" rows="10">${book.bookDescription}</textarea>
                <span></span>
            </p>
            <c:choose>
                <c:when test="${msg == '更新成功'}">
                    <span style="color: green">${msg}</span>
                </c:when>
                <c:otherwise>
                    <span style="color: red">${msg}</span>
                </c:otherwise>
            </c:choose>
            <p style="margin-top: 20px; margin-left: 100px">
                <input type="button" class="updateBtn" value="修改">
                <input type="button" class="backBtn" value="返回">
            </p>
        </form>
    </div>
</div>

<jsp:include page="${pageContext.request.contextPath}/background/common/footer.jsp"/>



<script type="application/javascript" src="${pageContext.request.contextPath}/statics/js/background/updateBook.js"></script>
<script type="application/javascript" src="${pageContext.request.contextPath}/statics/js/common.js"></script>

