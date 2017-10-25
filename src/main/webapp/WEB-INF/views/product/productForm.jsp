<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>产品管理</title>
</head>

<body>

	<form role="form" id="inputForm" action="${ctx}/product/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${product.id}" />
		<input type="hidden" name="image" id="product_image_value" value="${product.image}" />
		<section class="content">
			<div class="box box-info">
				<div class="box-header"><h3 class="box-title">
					<c:if test="${action eq 'create' }">新建产品</c:if>
					<c:if test="${action eq 'update' }">编辑产品</c:if></h3></div>
				<div class="box-body">
					<div class="row">
						<div class="col-md-12">
							<c:if test="${not empty message}">
								<div id="message" class="alert alert-success">
									<button data-dismiss="alert" class="close">×</button>${message}
								</div>
							</c:if>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-md-3 control-label">產品名:</label>
								<div class="col-md-9">
									<input class="form-control" type="text" id="productName" name="productName" value="${product.productName}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">編號:</label>
								<div class="col-md-9">
									<input class="form-control" type="text" id="productNo" name="productNo" value="${product.productNo}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">特征:</label>
								<div class="col-md-9">
									<textarea class="form-control" id="feature" name="feature" rows="5">${product.feature}</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">描述:</label>
								<div class="col-md-9">
									<textarea class="form-control" id="description" name="description" rows="5">${product.description}</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label" data-toggle="modal" data-target="#myModal">產品示意图</label>
								<div class="col-md-9">
									<div id="product_image" style="padding: 5px 0px 5px 0px;">
										<c:if test="${product.image != null}">
											<img class="col-md-12" src="${ctx}/file/dumpImage?path=${product.image}"  />
										</c:if>
									</div>
									<a class="btn btn-primary btn-70" data-toggle="modal" data-target="#myModal"><i class="fa fa-upload"></i> <spring:message code="public.upload" /></a>
									<p class="help-block"><i class="fa fa-fw fa-commenting"></i>图片上传后，需要点击保存生效.</p>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="box-footer">
					<button type="submit" class="btn btn-success btn-70"><i class="fa fa-save"></i> <spring:message code="public.save" /></button>
					<button type="button" class="btn btn-primary btn-70 disabled" onclick="window.location.href='${ctx}/product'">取消</button>
				</div>
			</div>
		</section>
	</form>

	<c:if test="${action eq 'update'}">
	<section class="content">
		<div class="box" style="top: -30px">
			<div class="box-header">
				<button type="button" class="btn btn-primary btn-70" onclick="scaleForm('${ctx}/product/scale/create/${product.id}')" data-toggle="modal" data-target="#scaleFormModal"><i class="fa fa-plus"></i> 新增</button>
			</div>
			<div class="box-body">
				<div class="table-responsive">
				<table class="table table-striped table-hover dataTable" style="margin-bottom:0px;">
					<thead>
						<tr>
							<th>规格</th>
							<th>12月/单价</th>
							<th>6月/单价</th>
							<th>3月/单价</th>
							<th>1月/单价</th>
							<th>库存</th>
							<th><spring:message code="public.oper" /></th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="scale" items="${scales}" varStatus="index">
						<tr class="${index.count%2==0?'odd':'even'}" id="scale_column_${scale.id}">
							<td>${scale.scale}</td>
							<td>${scale.twelveMonthPrice}</td>
							<td>${scale.sixMonthPrice}</td>
							<td>${scale.threeMonthPrice}</td>
							<td>${scale.oneMonthPrice}</td>
							<td>${scale.num}</td>
							<td>
								<a onclick="scaleForm('${ctx}/product/scale/update/${scale.id}')" data-toggle="modal" data-target="#scaleFormModal"><i class="fa fa-edit fa-fw"></i></a>
								<a onclick="delcfm('${ctx}/product/scale/delete/${scale.productId}/${scale.id}');"><i class="fa fa-times fa-fw"></i></a>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				</div>
			</div>
		</div>
	</section>
	</c:if>
	<!-- upload modal -->
	<jsp:include page="/WEB-INF/views/account/upload.jsp" flush="true">
		<jsp:param name="upload_url" value="/file/upload" />
	</jsp:include>
	<!-- scale form modal -->
	<div class="modal fade" id="scaleFormModal">

	</div>
	<%@ include file="/WEB-INF/views/include/confirmDel.jsp"%>
	<script>
		$(document).ready(function() {
			$("#inputForm").validate({
				rules : {
					productName : "required",
					productNo : "required",
					feature : "required",
					description : "required"
				},
				messages: {

				}
			});
		});

		function scaleForm(url){
			$.ajax({
				type: 'GET',
				url: url,
				dataType: 'html',
				success : function(html){
					console.log(html);
					$("#scaleFormModal").html(html);
				}
			});
		}
	</script>
</body>
</html>
