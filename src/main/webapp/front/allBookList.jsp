<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="${pageContext.request.contextPath}/front/common/header.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/front/common/navigation.jsp"/>

<div class="middle">

    <div class="allBookList">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>图书预约页面</span>
        </div>
        <div class="search">
            <form action="${pageContext.request.contextPath}/front/book.do">
                <input type="hidden" name="method" value="bookQuery">
                <input type="hidden" name="pageIndex" value="1">
                <span>书名：<input type="text" name="bookName" value="${bookName}"/></span>
                <span>作者：<input type="text" name="bookAuthor" value="${bookAuthor}"/></span>
                <span class="selectTime">时间：<input type="text" autocomplete="off" id="startDate" placeholder="开始日期" name="startDate" value="${startDate}"> —
                <input type="text" autocomplete="off" id="endDate" placeholder="结束日期" name="endDate" value="${endDate}"></span>
                <input	value="查 询" type="submit" id="searchButton">
            </form>
        </div>
        <table class="providerTable" cellpadding="0" cellspacing="0">
            <tr class="firstTr">
                <th width="200px">书籍名称</th>
                <th width="180px">书籍作者</th>
                <th width="180px">书籍类型</th>
                <th width="100px">出版日期</th>
                <th width="100px">书籍状态</th>
                <th width="100px">操作</th>
            </tr>
            <c:forEach var="book" items="${bookList}" varStatus="status">
                <tr>
                    <td>
                        <span>${book.bookName}</span>
                    </td>
                    <td>
                        <span>${book.bookAuthor}</span>
                    </td>
                    <td>
                        <span>${book.bookType}</span>
                    </td>
                    <td>
                        <span>${book.bookPublishDate}</span>
                    </td>
                    <td>
                    <span>
                        <c:if test="${book.bookStatus==1}"><span style="color: green">在架</span></c:if>
                        <c:if test="${book.bookStatus==2}"><span style="color: red">外借</span></c:if>
                    </span>
                    </td>
                    <td>
                        <span><a class="viewBook" href="javascript:;" bookId=${book.bookId}><img src="${pageContext.request.contextPath }/statics/images/detail.png" width="20px" height="20px" alt="查看" title="查看"/></a></span>
                        <span><a class="reserveBook" href="javascript:;" bookId=${book.bookId} bookStatus="${book.bookStatus}"><img src="${pageContext.request.contextPath }/statics/images/reserve.png" width="20px" height="20px" alt="预约" title="预约"/></a></span>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <%--页码--%>
        <c:import url="${pageContext.request.contextPath}/front/common/rollPage.jsp">
            <c:param name="currentPageNum" value="${currentPageNum}"/>
            <c:param name="pageNumList" value="${pageNumList}"/>
            <c:param name="totalPageNum" value="${totalPageNum}"/>
        </c:import>
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

<script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/front/allBookList.js"></script>

<jsp:include page="${pageContext.request.contextPath}/front/common/footer.jsp"/>