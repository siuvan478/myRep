<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title><spring:message code="user.title"/></title>
</head>

<body>
<form role="form" id="inputForm" action="${ctx}/profile/update" method="post" class="form-horizontal">
	<input type="hidden" name="id" value="${user.id}"/>
	<section class="content">
		<div class="box box-info">
			<div class="box-header"><h3 class="box-title">个人信息</h3></div>
			<div class="box-body">
				<div class="row">
					<div class="col-md-12">
						<c:if test="${not empty message}">
							<div id="message" class="alert alert-success alert-dismissable" role="alert">
								<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
								<strong>${message}</strong>
							</div>
						</c:if>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="col-md-3 control-label"><spring:message code="user.name" />:</label>
							<div class="col-md-9">
								<input class="form-control" type="text" id="name" name="name" value="${user.name}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">密码:</label>
							<div class="col-md-9">
								<input class="form-control" type="password" id="plainPassword" name="plainPassword" value="">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">确认密码:</label>
							<div class="col-md-9">
								<input class="form-control" type="password" id="confirmPassword" name="confirmPassword" value="">
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="box-footer">
				<button type="submit" class="btn btn-success btn-70"><i class="fa fa-save"></i> <spring:message code="public.save" /></button>
				<button type="button" class="btn btn-primary btn-70 disabled" onclick="window.location.href='${ctx}/area'">取消</button>
			</div>
		</div>
	</section>
</form>
	<script>
		$(document).ready(function() {
			$("#inputForm").validate({
				rules: {
					name:"required",
					plainPassword:{
						required: true,
						minlength: 6
					},
					confirmPassword:{
						required: true,
						minlength: 6,
						equalTo: "#plainPassword"
					}
			    }
			});
		});
	</script>
</body>
</html>
