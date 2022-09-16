<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/public.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/style.css">

<jsp:include page="${pageContext.request.contextPath}/front/common/header.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/front/common/navigation.jsp"/>


<div class="middle">
    <div class="bookInfo">
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
        <p>
            <a class="reserveBook" href="javascript:;" bookId=${book.bookId} bookStatus="${book.bookStatus}"><input type="button" class="reserveBtn" value="预约" style="margin-left: 125px; margin-top: 30px"></a>
            <input type="button" class="backBtn" value="返回" style="margin-left: 38px; margin-top: 30px">
        </p>
    </div>
</div>

<div class="zhezhao"></div>
<div class="noBook">
    <div class="noBookTip">
        <h2>提示</h2>
        <div class="noBookText">
            <p>抱歉，该书暂不在架</p>
            <a href="#" id="yes">确定</a>
        </div>
    </div>
</div>

</div>

<jsp:include page="${pageContext.request.contextPath}/front/common/footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/front/viewBook.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/common.js"></script>