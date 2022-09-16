<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/public.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/style.css">

<jsp:include page="${pageContext.request.contextPath}/background/common/header.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/background/common/sidebar.jsp"/>

<div class="admin_middle">
  <div style="position: relative">
    <div class="location" style="padding-left: 191px; padding-top: 80px">
      <strong>你现在所在的位置是:</strong>
      <span style="color: green">预约管理页面</span>
    </div>
    <div class="search" style="margin-top: 33px">
      <form action="${pageContext.request.contextPath}/background/book.do" style="display: inline-block" class="reserveManageForm">
        <input type="hidden" name="method" value="getAllReserve">
        <input type="hidden" name="pageIndex" value="1">
        <div style="display: inline-block;width: 206px" id="reserveUserName">用户名：<input type="text" name="userName" value="${userName}" style="float: right"/>
          <div class="likeName" style="display: none; opacity: 0.6; border: 0.5px solid; width: 140px; float: right; clear: both; position: relative; z-index: 999">
            <ul class="likeNameList">

            </ul>
          </div>
        </div>
        <span id="reserveId">预约号：<input type="text" name="reserveId" value="${reserveId}"></span>
        <span id="reserveStatus">状态：<select name="status" id="status">
                    <option value="-1" selected>全部</option>
                    <option value="1">有效</option>
                    <option value="2">即将逾期</option>
                    <option value="3">逾期</option>
                </select></span>
        <input	value="查 询" type="submit" id="searchButton">
      </form>
    </div>
    <table class="providerTable" cellpadding="0" cellspacing="0" style="margin: 40px auto;position: absolute;left: 220px; top: 176px">
      <tr class="firstTr">
        <th width="200px">用户名</th>
        <th width="180px">书籍名称</th>
        <th width="130px">预约日期</th>
        <th width="150px">归还日期</th>
        <th width="100px">状态</th>
        <th width="100px">操作</th>
      </tr>
      <c:forEach var="reserve" items="${reserveList}" varStatus="status">
        <tr>
          <td>
            <span>${reserve.userName}</span>
          </td>
          <td>
            <span>${reserve.bookName}</span>
          </td>
          <td>
            <span>${reserve.startDate}</span>
          </td>
          <td>
            <span>${reserve.endDate}</span>
          </td>
          <td>
            <c:choose>
              <c:when test="${reserve.status == 2}">
                <span style="color: orange">即将逾期</span>
              </c:when>
              <c:when test="${reserve.status == 3}">
                <span style="color: red">逾期</span>
              </c:when>
              <c:otherwise>
                <span style="color: green">有效</span>
              </c:otherwise>
            </c:choose>
          </td>
          <td>
            <span><a class="renewReserve" href="javascript:;" reserveId=${reserve.reserveId} bookId=${reserve.bookId} reserveStatus=${reserve.status}><img src="${pageContext.request.contextPath }/statics/images/renew.png" width="20px" height="20px" alt="续借" title="续借"/></a></span>
            <span><a class="notification" href="javascript:;" reserveId=${reserve.reserveId}><img src="${pageContext.request.contextPath }/statics/images/email.png" width="20px" height="20px" alt="通知" title="通知"/></a></span>
          </td>
        </tr>
      </c:forEach>
    </table>
    <%--页码--%>
    <div style="position: absolute; left: 543px; top: 518px">
      <c:import url="${pageContext.request.contextPath}/background/common/rollPage.jsp">
        <c:param name="currentPageNum" value="${currentPageNum}"/>
        <c:param name="pageNumList" value="${pageNumList}"/>
        <c:param name="totalPageNum" value="${totalPageNum}"/>
      </c:import>
    </div>

  </div>
</div>

<div class="zhezhao"></div>
<div class="renewDlg">
  <h2>续借图书</h2>
  <div class="renewDlgMain">
    <p></p>
    <button class="renewDlgOkBtn">确认</button>
  </div>
</div>


<jsp:include page="${pageContext.request.contextPath}/background/common/footer.jsp"/>

<script type="application/javascript" src="${pageContext.request.contextPath}/statics/js/common.js"></script>
<script type="application/javascript" src="${pageContext.request.contextPath}/statics/js/background/reserveManage.js"></script>

