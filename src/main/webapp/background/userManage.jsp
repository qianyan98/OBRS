<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/public.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/style.css">

<jsp:include page="${pageContext.request.contextPath}/background/common/header.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/background/common/sidebar.jsp"/>

<div class="admin_middle">
    <div>
        <div class="location" style="padding-left: 191px; padding-top: 80px">
            <strong>你现在所在的位置是:</strong>
            <span style="color: green">用户管理页面</span>
        </div>
        <div class="search" style="margin-top: 33px">
            <form action="${pageContext.request.contextPath}/background/user.do" style="display: inline-block">
                <input type="hidden" name="method" value="userQuery">
                <input type="hidden" name="pageIndex" value="1">
                <span>用户名：<input type="text" name="userName" value="${userName}"/></span>
                <span>性别：<select name="gender" id="gender">
                    <option value="-1" selected>全部</option>
                    <option value="1">女</option>
                    <option value="2">男</option>
                </select></span>
                <span>角色：<select name="userRole" id="userRole">
                    <option value="-1" selected>全部</option>
                    <c:forEach var="role" items="${roleList}">
                        <option value="${role.roleId}">${role.roleName}</option>
                    </c:forEach>
                </select></span>
                <input	value="查 询" type="submit" id="searchButton">
            </form>
        </div>
        <table class="providerTable" cellpadding="0" cellspacing="0" style="margin: 40px auto;">
            <tr class="firstTr">
                <th width="200px">用户名</th>
                <th width="180px">手机号码</th>
                <th width="130px">性别</th>
                <th width="150px">邮箱</th>
                <th width="100px">角色</th>
                <th width="100px">操作</th>
            </tr>
            <c:forEach var="user" items="${userList}" varStatus="status">
                <tr>
                    <td>
                        <span>${user.userName}</span>
                    </td>
                    <td>
                        <span>${user.userPhone}</span>
                    </td>
                    <td>
                        <c:if test="${user.gender==1}"><span>女</span></c:if>
                        <c:if test="${user.gender==2}"><span>男</span></c:if>
                    </td>
                    <td>
                        <span>${user.userEmail}</span>
                    </td>
                    <td>
                    <span>
                        <span>${user.userRoleName}</span>
                    </span>
                    </td>
                    <td>
                        <span><a class="viewUser" href="javascript:;" userId=${user.userId}><img src="${pageContext.request.contextPath }/statics/images/detail.png" width="20px" height="20px" alt="查看" title="查看"/></a></span>
                        <span><a class="deleteUser" href="javascript:;" userId=${user.userId} userRole=${user.userRoleName}><img src="${pageContext.request.contextPath }/statics/images/delete.png" width="20px" height="20px" alt="删除" title="删除"/></a></span>
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

<div class="zhezhao"></div>

<div class="deleteUserDlg">
    <h2>删除用户</h2>
    <div class="deleteUserDlgMain">
        <p style="color: red" class="deleteUserDlgContent">是否确定删除该用户？</p>
        <p>
            <button type="button" class="cancelBtn">取消</button>
            <button type="button" class="OkBtn">确定</button>
        </p>
    </div>
</div>

<jsp:include page="${pageContext.request.contextPath}/background/common/footer.jsp"/>

<script type="application/javascript" src="${pageContext.request.contextPath}/statics/js/common.js"></script>
<script type="application/javascript" src="${pageContext.request.contextPath}/statics/js/background/userManage.js"></script>

