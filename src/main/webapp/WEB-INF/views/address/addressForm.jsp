<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
	<title>取货地址管理</title>
</head>
<body>

<form role="form" id="inputForm" action="${ctx}/address/${action}" method="post" class="form-horizontal">
	<input type="hidden" name="id" value="${address.id}" />
	<section class="content">
		<div class="box box-info">
			<div class="box-header"><h3 class="box-title">取货地址管理</div>
			<div class="box-body">
				<div class="row">
					<div class="col-md-12">
						<c:if test="${not empty message}">
							<div id="message" class="alert alert-success" role="alert">
								<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
								<strong>${message}</strong>
							</div>
						</c:if>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="col-md-3 control-label">联系人:</label>
							<div class="col-md-9">
								<input class="form-control" type="text" value="${address.contactName}" readonly>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">手机号:</label>
							<div class="col-md-9">
								<input class="form-control" type="text" value="${address.contactPhone}" readonly>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">电子邮箱:</label>
							<div class="col-md-9">
								<input class="form-control" type="text" value="${address.contactEmail}" readonly>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">区域:</label>
							<div class="col-md-9">
								<tags:selectbox name="areaId" map="${areas}" value="${address.areaId}" empty="true"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">地址:</label>
							<div class="col-md-9">
								<input class="form-control" type="text" id="address" name="address" value="${address.address}">
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="box-footer">
				<button type="submit" class="btn btn-success btn-70"><i class="fa fa-save"></i> <spring:message code="public.save" /></button>
				<button type="button" class="btn btn-primary btn-70 disabled" onclick="window.location.href='${ctx}/address'">取消</button>
			</div>
		</div>
	</section>
</form>

<script>
	$(document).ready(function() {
		$("#inputForm").validate({
			rules : {
				name : "required",
				areaId : "required",
				address : "required"
			},
			messages: {

			}
		});
	});
</script>
</body>
</html>