<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/public.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/style.css">

<jsp:include page="${pageContext.request.contextPath}/background/common/header.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/background/common/sidebar.jsp"/>

<div class="admin_middle">
    <div class="addBookMain">
        <form action="${pageContext.request.contextPath}/background/book.do?method=addBook" method="post" class="addBookForm">
            <p>
                <span>书&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span>
                <input type="text" name="bookName" value="${book.bookName}" style="width: 400px;border: 1px solid">
                <span></span>
            </p>
            <p>
                <span>作&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;者：</span>
                <input type="text" name="bookAuthor" value="${book.bookAuthor}" style="width: 400px;border: 1px solid">
                <span></span>
            </p>
            <p>
                <span>类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：</span>
                <input type="text" name="bookType" value="${book.bookType}" contenteditable="true" style="width: 400px;border: 1px solid">
                <span></span>
            </p>
            <p>
                <span>出版日期：</span>
                <input type="text" name="bookPublishDate" id="publishDate" value="${bookPublishDate}" style="width: 400px;border: 1px solid">
                <span></span>
            </p>
            <p style="padding-top: 8px">
                <span class="bookIntroTip">简&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;介：</span>
                <textarea name="bookDescription" class="bookIntro" cols="50" rows="10" style="border: 1px solid; margin-left: 10px; border-radius: 5px">${book.bookDescription}</textarea>
                <span></span>
            </p>
            <c:choose>
                <c:when test="${msg == '添加成功'}">
                    <span style="color: green">${msg}</span>
                </c:when>
                <c:otherwise>
                    <span style="color: red">${msg}</span>
                </c:otherwise>
            </c:choose>
            <p style="margin-top: 30px; margin-left: 100px">
                <input type="button" class="addBookBtn" value="添加">
                <input type="button" class="backBtn" value="返回">
            </p>
        </form>
    </div>
</div>

<jsp:include page="${pageContext.request.contextPath}/background/common/footer.jsp"/>

<script type="application/javascript" src="${pageContext.request.contextPath}/statics/js/common.js"></script>
<script type="application/javascript" src="${pageContext.request.contextPath}/statics/js/background/addBook.js"></script>
