<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="${pageContext.request.contextPath}/front/common/header.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/front/common/navigation.jsp"/>

<div class="middle">
    <div class="reserveDiv">
        <form action="/front/book.do" method="post" class="reserveForm">
            <input type="hidden" name="method" value="reserveBook">
            <input type="hidden" name="bookId" value="${book.bookId}">
            <p><span>书&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：<input type="text" readonly value="${book.bookName}"></span></p>
            <p><span>作&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;者：<input type="text" readonly value="${book.bookAuthor}"></span></p>
            <p><span>类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型：<input type="text" readonly value="${book.bookType}"></span></p>
            <p><span>出版时间：<input type="text" readonly value="${book.bookPublishDate}"></span></p>
            <%--预约开始时间：默认为是预约操作发生的日期--%>
            <input type="hidden" name="startDate" value="${startDate}">
            <%--选择预约结束时间--%>
            <p>
                <span>预约时间：<input type="text" autocomplete="off" id="reserveEndDate" placeholder="预约结束日期" name="reserveEndDate" value="${reserveEndDate}"><span style="margin-left: 10px"></span></span>
            </p>
            <p>
                <c:choose>
                    <c:when test="${msg == '预约成功'}">
                        <span class="reserveTip" style="color: green">${msg}</span>
                    </c:when>
                    <c:otherwise>
                        <span class="reserveTip" style="color: red">${msg}</span>
                    </c:otherwise>
                </c:choose>
            </p>
            <p>
                <input type="button" value="预约" class="reserveBtn">
                <input type="button" value="返回" class="backBtn">
            </p>
        </form>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/front/reserveBook.js"></script>

<jsp:include page="${pageContext.request.contextPath}/front/common/footer.jsp"/>
