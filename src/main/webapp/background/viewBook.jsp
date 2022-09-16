<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/public.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/style.css">

<jsp:include page="${pageContext.request.contextPath}/background/common/header.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/background/common/sidebar.jsp"/>

<div class="admin_middle">
    <div class="bookInfo_admin">
        <p>
            <span>书&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span>
            <input type="text" value="${book.bookName}" disabled style="width: 400px;">
        </p>
        <p>
            <span>作&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;者：</span>
            <input type="text" value="${book.bookAuthor}" disabled style="width: 400px;">
        </p>
        <p>
            <span>类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：</span>
            <input type="text" value="${book.bookType}" disabled contenteditable="true" style="width: 400px">
        </p>
        <p>
            <span>出版日期：</span>
            <input type="text" value="${book.bookPublishDate}" disabled style="width: 400px;">
        </p>
        <p>
            <span class="bookIntroTip">简&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;介：</span>
            <textarea name="bookIntro" class="bookIntro" cols="50" rows="10" disabled>${book.bookDescription}</textarea>
        </p>
        <span style="color: red">${msg}</span>
        <p>
            <a class="updateBook" href="javascript:;" bookId=${book.bookId}><input type="button" class="updateBtn" value="更新" style="margin-left: 125px; margin-top: 30px"></a>
            <input type="button" class="backBtn" value="返回" style="margin-left: 38px; margin-top: 30px">
        </p>
    </div>
</div>

<jsp:include page="${pageContext.request.contextPath}/background/common/footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/background/viewBook.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/common.js"></script>