<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>配置</title>
</head>

<body>

<form role="form" id="inputForm" action="${ctx}/config/update" method="post" class="form-horizontal">
	<section class="content">
		<div class="box box-info">
			<div class="box-header"><h3 class="box-title">业务配置</h3></div>
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
							<label class="col-md-3 control-label">预约价格(全价):</label>
							<div class="col-md-9">
								<input class="form-control" type="text" id="commonFee" name="commonFee" value="${config.commonFee}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">全价次数:</label>
							<div class="col-md-9">
								<input class="form-control" type="text" id="number" name="number" value="${config.number}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">预约优惠价格:</label>
							<div class="col-md-9">
								<input class="form-control" type="text" id="discountFee" name="discountFee" value="${config.discountFee}">
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
				rules : {
					commonFee:{
						required:true,
						number:true,
						min:0
					},
					number : {
						required:true,
						number:true,
						min:0
					},
					discountFee : {
						required:true,
						number:true,
						min:0
					}
				},
				messages: {

				}
			});
		});
	</script>
</body>
</html>
