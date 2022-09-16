<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="pageNum">
  <ul class="pagination">
      <c:choose>
          <c:when test="${param.currentPageNum > 1}">
              <li class="forward"><a href="javascript:pageNav(document.forms[0], ${param.currentPageNum - 1})"><<</a></li>
          </c:when>
          <c:otherwise>
              <li><span><<</span></li>
          </c:otherwise>
      </c:choose>
      <c:choose>
          <c:when test="${param.currentPageNum < 5}">
              <c:forEach var="pageNum" items="${requestScope.pageNumList}">
                  <c:if test="${pageNum <= 6}">
                      <c:choose>
                          <c:when test="${pageNum != param.currentPageNum}">
                              <li><a href="javascript:pageNav(document.forms[0], ${pageNum})">${pageNum}</a></li>
                          </c:when>
                          <c:otherwise>
                              <li><span style="background: #C9C9C9">${pageNum}</span></li>
                          </c:otherwise>
                      </c:choose>
                  </c:if>
              </c:forEach>
          </c:when>
          <c:otherwise>
              <c:forEach var="pageNum" items="${requestScope.pageNumList}">
                  <c:if test="${((pageNum - param.currentPageNum) <= 2 and pageNum >= param.currentPageNum)
                  or ((param.currentPageNum - pageNum) <= 3 and pageNum <= param.currentPageNum)}">
                      <c:choose>
                          <c:when test="${pageNum != param.currentPageNum}">
                              <li><a href="javascript:pageNav(document.forms[0], ${pageNum})">${pageNum}</a></li>
                          </c:when>
                          <c:otherwise>
                              <li><span style="background: #C9C9C9">${pageNum}</span></li>
                          </c:otherwise>
                      </c:choose>
                  </c:if>
              </c:forEach>
          </c:otherwise>
      </c:choose>
      <c:choose>
          <c:when test="${param.currentPageNum < param.totalPageNum}">
              <li class="backward"><a href="javascript:pageNav(document.forms[0], ${param.currentPageNum + 1})">>></a></li>
          </c:when>
          <c:otherwise>
              <li><span>>></span></li>
          </c:otherwise>
      </c:choose>

  </ul>
</div>

<script>
    function pageNav(form, num) {
        form.pageIndex.value = num;
        form.submit();
    }
</script>
