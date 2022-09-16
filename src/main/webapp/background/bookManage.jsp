<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="${pageContext.request.contextPath}/background/common/header.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/background/common/sidebar.jsp"/>

<div class="admin_middle">

    <div class="bookManageMain">
        <div class="location" style="padding-left: 191px; padding-top: 80px">
            <strong>你现在所在的位置是:</strong>
            <span style="color: green">书籍管理页面</span>
        </div>
        <div class="search" style="margin-top: 33px">
            <form action="${pageContext.request.contextPath}/background/book.do" style="display: inline-block">
                <input type="hidden" name="method" value="bookQuery">
                <input type="hidden" name="pageIndex" value="1">
                <span>书名：<input type="text" name="bookName" value="${bookName}"/></span>
                <span>作者：<input type="text" name="bookAuthor" value="${bookAuthor}"/></span>
                <span class="selectTime">时间：<input type="text" autocomplete="off" id="startDate" placeholder="开始日期" name="startDate" value="${startDate}"> —
                <input type="text" autocomplete="off" id="endDate" placeholder="结束日期" name="endDate" value="${endDate}"></span>
                <input	value="查 询" type="submit" id="searchButton">
            </form>
            <input type="button" style="display: inline-block; margin-left: 10px; width: 60px; height: 30px" value="添加图书"/>
        </div>
        <table class="providerTable" cellpadding="0" cellspacing="0" style="margin: 40px auto;">
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
                        <span><a class="deleteBook" href="javascript:;" bookId=${book.bookId} bookStatus="${book.bookStatus}"><img src="${pageContext.request.contextPath }/statics/images/delete.png" width="20px" height="20px" alt="删除" title="删除"/></a></span>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <%--页码--%>
        <c:import url="${pageContext.request.contextPath}/background/common/rollPage.jsp">
            <c:param name="currentPageNum" value="${currentPageNum}"/>
            <c:param name="pageNumList" value="${pageNumList}"/>
            <c:param name="totalPageNum" value="${totalPageNum}"/>
        </c:import>
    </div>

</div>

<div class="zhezhao">

</div>

<div class="deleteBookDlg">
    <h2>删除图书</h2>
    <div class="deleteBookDlgMain">
        <p style="color: red" class="deleteBookDlgContent">是否确定删除该书？</p>
        <p>
            <button type="button" class="cancelBtn">取消</button>
            <button type="button" class="OkBtn">确定</button>
        </p>
    </div>
</div>

<jsp:include page="${pageContext.request.contextPath}/background/common/footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/background/bookManage.js"></script>