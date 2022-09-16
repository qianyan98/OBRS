<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/public.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/style.css">

<jsp:include page="${pageContext.request.contextPath}/background/common/header.jsp"/>
<jsp:include page="${pageContext.request.contextPath}/background/common/sidebar.jsp"/>

<div class="admin_middle">
    <div class="changePwdMain">
        <form action="${pageContext.request.contextPath}/background/user.do" method="post" class="changePwdForm">
            <input type="hidden" name="method" value="changePwd">
            <p>
                <span>旧&nbsp;密&nbsp;码&nbsp;&nbsp;：</span>
                <input type="password" name="oldPwd">
                <img src="${pageContext.request.contextPath}/statics/images/pwdShow.png" width="20px" height="20px" class="adjustPwd">
                <span style="width: 150px"></span>
            </p>
            <p>
                <span>新&nbsp;密&nbsp;码&nbsp;&nbsp;：</span>
                <input type="password" name="newPwd">
                <img src="${pageContext.request.contextPath}/statics/images/pwdShow.png" width="20px" height="20px" class="adjustPwd">
                <span style="width: 150px"></span>
            </p>
            <p>
                <span>确认密码：</span>
                <input type="password" name="confirmPwd">
                <img src="${pageContext.request.contextPath}/statics/images/pwdShow.png" width="20px" height="20px" class="adjustPwd">
                <span style="width: 150px"></span>
            </p>
            <p>
                <input type="button" value="修改" class="changePwdBtn">
                <span style="margin-left: 10px; color: green">${msg}</span>
            </p>
        </form>
    </div>
</div>

<div class="zhezhao">
</div>

<div class="changePwdDlg">
    <div class="changePwdDlgMain">
        <h2>修改密码</h2>
        <div class="changePwdDlgContent">
            是否确认修改密码？
        </div>
        <p>
            <input type="button" name="cancel" value="取消" class="cancelBtn">
            <input type="button" name="submit" value="确认" class="OkBtn">
        </p>
    </div>
</div>

<jsp:include page="${pageContext.request.contextPath}/background/common/footer.jsp"/>

<script type="application/javascript" src="${pageContext.request.contextPath}/statics/js/common.js"></script>
<script type="application/javascript" src="${pageContext.request.contextPath}/statics/js/background/changePwd.js"></script>

