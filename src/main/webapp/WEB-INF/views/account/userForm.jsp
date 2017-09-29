<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title><spring:message code="user.title" /></title>
</head>

<body>
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">
				<spring:message code="user.title" />
			</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<form role="form" id="inputForm" action="${ctx}/user/${action}"
		method="post">
		<!-- /.row -->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-6">
								<input type="hidden" name="id" value="${user.id}" />
								<c:if test="${not empty message}">
									<div id="message" class="alert alert-success">
										<button data-dismiss="alert" class="close">×</button>${message}</div>
								</c:if>
								<div class="form-group">
									<label><spring:message code="user.name" />:</label>
									<input class="form-control" type="text" id="name" name="name" value="${user.name}">
								</div>
								<div class="form-group">
									<label><spring:message code="index.loginname" />:</label>
									<input class="form-control" type="text" id="loginName" name="loginName" value="${user.loginName}"
									<c:if test="${action=='update'}">disabled="disabled"</c:if>>
								</div>
								<c:if test="${ action == 'create' }">
									<div class="form-group">
										<label><spring:message code="index.password" />:</label>
										<input class="form-control" type="password" id="plainPassword" name="plainPassword" />
									</div>
									<div class="form-group">
										<label><spring:message code="user.confirmpassword" />:</label>
										<input class="form-control" type="password" id="confirmPassword" name="confirmPassword" />
									</div>
								</c:if>
								<div class="form-group">
									<label><spring:message code="user.email" />:</label> <input
										class="form-control" type="email" id="email" name="email"
										value="${user.email }" />
								</div>
								<button id="submit_btn" type="submit" class="btn btn-success">
									<spring:message code="public.commit" />
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
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
