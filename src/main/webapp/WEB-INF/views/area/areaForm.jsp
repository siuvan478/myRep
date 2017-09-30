<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>区域管理</title>
</head>

<body>
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">
				区域管理
			</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<form role="form" id="inputForm" action="${ctx}/area/${action}" method="post">
		<!-- /.row -->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-6">
								<input type="hidden" name="id" value="${area.id}" />
								<c:if test="${not empty message}">
									<div id="message" class="alert alert-success">
										<button data-dismiss="alert" class="close">×</button>${message}
									</div>
								</c:if>
								<div class="form-group">
									<label>选择城市:</label>
									<tags:selectbox name="cityId" map="${cities}" value="${area.cityId}" />
								</div>
								<div class="form-group">
									<label>区域名称:</label>
									<input class="form-control" type="text" id="name" name="name" value="${area.name}">
								</div>
								<div class="form-group">
									<label>区域名称(英文):</label>
									<input class="form-control" type="text" id="nameEN" name="nameEN" value="${area.nameEN}" />
								</div>
								<button id="submit_btn" type="submit" class="btn btn-success">
									<i class="fa fa fa-save"></i>
									<spring:message code="public.save" />
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
					nameEN : "required",

				},
				messages: {

				}
			});
		});
	</script>
</body>
</html>
