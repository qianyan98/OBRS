<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/public.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/style.css">

<jsp:include page="${pageContext.request.contextPath}/front/common/header.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/front/common/navigation.jsp"/>

<div class="middle">
    <div class="myReserve">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>我的预约页面</span>
        </div>
        <div class="search">
            <form action="${pageContext.request.contextPath}/front/book.do">
                <input type="hidden" name="method" value="reserveQuery">
                <input type="hidden" name="pageIndex" value="1">
                <span>书名：<input type="text" name="bookName" value="${bookName}"/></span>
                <span>作者：<input type="text" name="bookAuthor" value="${bookAuthor}"/></span>
                <span class="selectTime">时间：<input type="text" autocomplete="off" id="startDate" placeholder="预约起始日期" name="startDate" value="${startDate}"> —
                <input type="text" autocomplete="off" id="endDate" placeholder="预约结束日期" name="endDate" value="${endDate}"></span>
                <input	value="查 询" type="submit" id="searchButton">
            </form>
        </div>
        <c:if test="${msg != ''}">
            <span style="color: red; display: block; padding: 40px 0 0 15px">${msg}</span>
        </c:if>
        <table class="providerTable" cellpadding="0" cellspacing="0">
            <tr class="firstTr">
                <th width="200px">书籍名称</th>
                <th width="180px">书籍作者</th>
                <th width="180px">出版日期</th>
                <th width="100px">预约日期</th>
                <th width="100px">归还日期</th>
                <th width="100px">操作</th>
            </tr>
            <c:forEach var="reserve" items="${reserveList}" varStatus="status">
                <tr>
                    <td>
                        <span>${reserve.bookName}</span>
                    </td>
                    <td>
                        <span>${reserve.bookAuthor}</span>
                    </td>
                    <td>
                        <span>${reserve.bookPublishDate}</span>
                    </td>
                    <td>
                        <span>${reserve.startDate}</span>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${reserve.reserveStatus == 2}">
                                <span style="color: orange">${reserve.endDate}</span>
                            </c:when>
                            <c:when test="${reserve.reserveStatus == 3}">
                                <span style="color: red">${reserve.endDate}</span>
                            </c:when>
                            <c:otherwise>
                                <span>${reserve.endDate}</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <span><a class="returnBook" href="javascript:;" bookId=${reserve.bookId} reserveId=${reserve.reserveId}><img src="${pageContext.request.contextPath }/statics/images/return.png" width="20px" height="20px" alt="归还" title="归还"/></a></span>
                        <span><a class="renewBook" href="javascript:;" bookId=${reserve.bookId} count=${reserve.count} reserveId=${reserve.reserveId} reserveStatus=${reserve.reserveStatus}><img src="${pageContext.request.contextPath }/statics/images/renew.png" width="20px" height="20px" alt="续借" title="续借"/></a></span>
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
<div class="deleteDlg">
    <h2>归还图书</h2>
    <div class="deleteDlgMain">
        <p>是否确认归还该书？</p>
        <button class="cancelBtn">取消</button>
        <button class="OkBtn">确认</button>
    </div>
</div>

<div class="renewDlg">
    <h2>续借图书</h2>
    <div class="renewDlgMain">
        <p></p>
        <button class="renewDlgOkBtn">确认</button>
    </div>
</div>


<jsp:include page="${pageContext.request.contextPath}/front/common/footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/front/myReserve.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/common.js"></script>
