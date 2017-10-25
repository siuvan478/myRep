<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>会员管理</title>
</head>

<body>

	<form role="form" id="inputForm" action="${ctx}/user/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${user.id}" />
		<section class="content">
			<div class="box box-">
				<div class="box-header"><h3 class="box-title"><c:if test="${action eq 'create' }">新建会员</c:if>
					<c:if test="${action eq 'update' }">编辑会员</c:if></h3></div>
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
									<input class="form-control" type="email" id="email" name="email" value="${user.email }" placeholder="@"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">手机号:</label>
								<div class="col-md-9">
									<input class="form-control" type="text" id="phone" name="phone" value="${user.phone }" placeholder=""/>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="box-footer">
					<button type="submit" class="btn btn-success btn-70"><i class="fa fa-save"></i> <spring:message code="public.save" /></button></button>
					<button type="button" class="btn btn-primary btn-70 disabled" onclick="window.location.href='${ctx}/user'">取消</button>
				</div>
			</div>
		</section>
	</form>
	<script>
		$(document).ready(function() {

			// 手机号码验证
			jQuery.validator.addMethod("isMobileOrPhone", function(value, element) {
				var length = value.length;
				var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
				var phone = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/;
				return this.optional(element) || ((length == 11 && mobile.test(value) || (length != 11 && phone.test(value)) ) );
			}, "请输入有效的手机号");

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
					},
					phone:{
						required : false,
						isMobileOrPhone : true
					}
				},
				messages: {
					loginName: {
						remote: '<spring:message code="user.loginName.exist" />'
					}
				}
			});
		});
	</script>
</body>
</html>
