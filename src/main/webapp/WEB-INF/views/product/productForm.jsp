<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>	<c:if test="${action eq 'create' }"><spring:message code="product.form.create.title" /></c:if>
	<c:if test="${action eq 'update' }"><spring:message code="product.form.edit.title" /></c:if></title>
</head>

<body>

	<form role="form" id="inputForm" action="${ctx}/product/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${product.id}" />
		<input type="hidden" name="image" id="image_value" value="${product.image}" />
		<input type="hidden" name="imageDetail" id="imageDetail_value" value="${product.imageDetail}" />
		<section class="content">
			<div class="box box-info">
				<div class="box-header"><h3 class="box-title">
					<c:if test="${action eq 'create' }"><spring:message code="product.form.create.title" /></c:if>
					<c:if test="${action eq 'update' }"><spring:message code="product.form.edit.title" /></c:if></h3></div>
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
								<label class="col-md-3 control-label"><spring:message code="product.form.productName" />:</label>
								<div class="col-md-9">
									<input class="form-control" type="text" id="productName" name="productName" value="${product.productName}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label"><spring:message code="product.form.productNo" />:</label>
								<div class="col-md-9">
									<input class="form-control" type="text" id="productNo" name="productNo" value="${product.productNo}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label"><spring:message code="product.form.feature" />:</label>
								<div class="col-md-9">
									<textarea class="form-control" id="feature" name="feature" rows="5">${product.feature}</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label"><spring:message code="product.form.description" />:</label>
								<div class="col-md-9">
									<textarea class="form-control" id="description" name="description" rows="5">${product.description}</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label"><spring:message code="product.form.image" /></label>
								<div class="col-md-9">
									<div id="image_div" style="padding: 5px 0px 5px 0px;">
										<c:if test="${product.image != null}">
											<img class="col-md-12" src="${ctx}/file/dumpImage?path=${product.image}"  />
										</c:if>
									</div>
									<a class="btn btn-primary btn-100" onclick="showImageUploadModal('image')" data-toggle="modal"
									   data-target="#image_modal"><i class="fa fa-upload"></i> <spring:message code="public.upload" /></a>
									<p class="help-block"><i class="fa fa-fw fa-commenting"></i><spring:message code="public.image.tips" /></p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label"><spring:message code="product.form.imageDetail" /></label>
								<div class="col-md-9">
									<div id="imageDetail_div" style="padding: 5px 0px 5px 0px;">
										<c:if test="${product.imageDetail != null}">
											<img class="col-md-12" src="${ctx}/file/dumpImage?path=${product.imageDetail}"  />
										</c:if>
									</div>
									<a class="btn btn-primary btn-100" onclick="showImageUploadModal('imageDetail')"
									   data-toggle="modal" data-target="#imageDetail_modal"><i class="fa fa-upload"></i> <spring:message code="public.upload" /></a>

									<p class="help-block"><i class="fa fa-fw fa-commenting"></i><spring:message code="public.image.tips" /></p>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="box-footer">
					<button type="submit" class="btn btn-success btn-70"><i class="fa fa-save"></i> <spring:message code="public.save" /></button>
					<button type="button" class="btn btn-primary btn-70 disabled" onclick="window.location.href='${ctx}/product'"><spring:message code="public.cancel" /></button>
				</div>
			</div>
		</section>
	</form>

	<c:if test="${action eq 'update'}">
	<section class="content">
		<div class="box" style="top: -30px">
			<div class="box-header">
				<button type="button" class="btn btn-primary btn-70" onclick="scaleForm('${ctx}/product/scale/create/${product.id}')"
						data-toggle="modal" data-target="#scaleFormModal"><i class="fa fa-plus"></i> <spring:message code="public.add"/></button>
			</div>
			<div class="box-body">
				<div class="table-responsive">
				<table class="table table-striped table-hover dataTable" style="margin-bottom:0px;">
					<thead>
						<tr>
							<th><spring:message code="scale.body.name" /></th>
							<th><spring:message code="scale.body.twelveMonthPrice" /></th>
							<th><spring:message code="scale.body.sixMonthPrice" /></th>
							<th><spring:message code="scale.body.threeMonthPrice" /></th>
							<th><spring:message code="scale.body.oneMonthPrice" /></th>
							<th><spring:message code="scale.body.number" /></th>
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
	<!-- scale form modal -->
	<div class="modal fade" id="scaleFormModal">

	</div>
	<div class="modal fade" id="image_modal">

	</div>
	<div class="modal fade" id="imageDetail_modal">

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

		function showImageUploadModal(imageType){
			if(imageType == 'image'){
				$("#imageDetail_modal").html('');
			}else if(imageType == 'imageDetail'){
				$("#image_modal").html('');
			}
			$.ajax({
				type: 'GET',
				url: '${ctx}/product/showUploadModal/' + imageType,
				dataType: 'html',
				success : function(html){
					$("#" + imageType + "_modal").html(html);
				}
			});
		}

		function scaleForm(url){
			$.ajax({
				type: 'GET',
				url: url,
				dataType: 'html',
				success : function(html){
					$("#scaleFormModal").html(html);
				}
			});
		}
	</script>
</body>
</html>
