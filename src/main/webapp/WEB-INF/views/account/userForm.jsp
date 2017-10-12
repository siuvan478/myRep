<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title><spring:message code="user.title" /></title>
</head>



<body>

	<!-- Content Header -->
	<section class="content-header">
		<h1>
			<c:if test="${action eq 'create' }">新建用户</c:if>
			<c:if test="${action eq 'update' }">编辑用户</c:if>
		</h1>
	</section>

	<form role="form" id="inputForm" action="${ctx}/user/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${user.id}" />
		<section class="content">
			<div class="box box-info">
				<div class="box-body">
					<div class="row">
						<div class="col-md-8">
							<c:if test="${not empty message}">
								<div id="message" class="alert alert-success">
									<button data-dismiss="alert" class="close">×</button>${message}</div>
							</c:if>
							<div class="form-group">
								<label class="col-md-3 control-label"><spring:message code="user.name" />:</label>
								<div class="col-md-9">
									<input class="form-control" type="text" id="name" name="name" value="${user.name}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label"><spring:message code="index.loginname" />:</label>
								<div class="col-md-9">
									<input class="form-control" type="text" id="loginName" name="loginName" value="${user.loginName}"
									<c:if test="${action=='update'}">disabled="disabled"</c:if>>
								</div>
							</div>
							<c:if test="${ action == 'create' }">
								<div class="form-group">
									<label class="col-md-3 control-label"><spring:message code="index.password" />:</label>
									<div class="col-md-9">
										<input class="form-control" type="password" id="plainPassword" name="plainPassword" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label"><spring:message code="user.confirmpassword" />:</label>
									<div class="col-md-9">
										<input class="form-control" type="password" id="confirmPassword" name="confirmPassword" />
									</div>
								</div>
							</c:if>
							<div class="form-group">
								<label class="col-md-3 control-label"><spring:message code="user.email" />:</label>
								<div class="col-md-9">
									<input class="form-control" type="email" id="email" name="email" value="${user.email }" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">手机号:</label>
								<div class="col-md-9">
									<input class="form-control" type="text" id="phone" name="phone" value="${user.phone }" />
								</div>
							</div>

							<div class="box-footer">
								<button type="submit" class="btn btn-success btn-65"><spring:message code="public.save" /></button>
								<button type="button" class="btn btn-primary btn-65 disabled" onclick="window.location.href='${ctx}/user'">取消</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</form>
	<script>
		$(document).ready(function() {
			$("#inputForm").validate({
				rules : {
					name : "required",
					loginName : {
						required : true,
						remote : { 
							url : "${ctx}/user/checkLoginName",
							type : "post",
							dataType: 'json',
							data : {
								loginName : function() {
									return $("#loginName").val();
								}
							},
							dataFilter : function(data, type) {
								if (data == "true")
									return true;
								else
									return false;
							}
						}
					},

					roles : "required",
					email : {
						required : true,
						email : true
					},
					plainPassword : {
						required : true,
						minlength : 6
					},
					confirmPassword : {
						required : true,
						minlength : 6,
						equalTo : "#plainPassword"
					}
				},
				messages: {
					loginName: {
						remote: '<spring:message code="user.loginName.exist" />'
					}
				}
			});

			$('#roles').change(function() {
				var role = $(this).val();
				if (role != 'admin') {
					$("#emailauditDiv").show();
					$("#financeEmailDiv").hide();
					if (role == 'finance') {
						$("#financeEmailDiv").show();
					}

				} else {
					$("#emailauditDiv").hide();
					$("#financeEmailDiv").hide();
					$("#emailaudit").val("N");
				}
			});

			var role = $("#roles").val();
			if (role != 'admin') {
				$("#emailauditDiv").show();
				$("#financeEmailDiv").hide();
				if (role == 'finance') {
					$("#financeEmailDiv").show();
				}
			} else {
				$("#emailauditDiv").hide();
				$("#financeEmailDiv").hide();
				$("#emailaudit").val("N");
			}

		});
	</script>
</body>
</html>
