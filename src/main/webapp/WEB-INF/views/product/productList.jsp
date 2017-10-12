<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>产品列表</title>
</head>
<body>
	<br />
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<form id="searchForm" action="${ctx}/product" method="get">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-info">
					<div class="panel-heading">
						产品列表
						<small></small>
						<button type="button" class="btn-mini btn-link pull-right search-plus-minus">
							<i class="fa fa-search-minus"></i>
						</button>
					</div>
					<div class="panel-body panel-body-search">
						<div class="row">
							<div class="col-lg-4">
								<div class="form-group">
									<label for="productName">產品名</label>
									<input type="text" class="form-control" id="productName" name="productName" value="<c:out value="${pages.searchMap['productName']}"/>" placeholder="Enter Product name...">
								</div>
							</div>
						</div>
					</div>
					<div class="panel-footer panel-footer-search">
						<div class="btn-group" role="group" aria-label="...">
							<button type="submit" class="btn  btn-info">
								<i class="fa fa-search"></i>
								<spring:message code="public.search" />
							</button>
							<button id="resetButton" type="button" class="btn  btn-warning">
								<i class="fa fa-repeat"></i>
								<spring:message code="public.reset" />
							</button>
						</div>

						<a class="btn  btn-primary pull-right" href="${ctx}/product/create"><i
							class="fa fa-edit"></i> <spring:message code="public.create" /></a>
					</div>
				</div>
			</div>
		</div>
	</form>

	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="table-responsive">
						<table class="table table-striped table-hover dataTable" style="margin-bottom:0px;">
							<thead>
								<tr>
									<th <tags:sort column="productName" page="${pages}"/>>產品名</th>
									<th <tags:sort column="productNo" page="${pages}"/>>編號</th>
									<th><spring:message code="public.oper" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pages.content}" var="product" varStatus="index">
									<tr class="${index.count%2==0?'odd':'even'}">
										<td>${product.productName}</td>
										<td>${product.productNo}</td>
										<td>
											<a href="${ctx}/product/update/${product.id}"><i class="fa fa-pencil fa-fw"></i></a>
											<a onclick="delcfm('${ctx}/product/delete/${product.id}');"><i class="fa fa-times fa-fw"></i></a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<!-- /.table-responsive -->
					
					<tags:pagination page="${pages}" paginationSize="5" />
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
	</div>

	<%@ include file="/WEB-INF/views/include/confirmDel.jsp"%>
	<script>
		$(document).ready(function() {
			$(".search-plus-minus").on("click", function() {
				var i = $(this).children(":first");
				if (i.attr("class").indexOf("fa-search-minus") > 0) {
					i.removeClass("fa-search-minus")
					i.addClass("fa-search-plus")
				} else {
					i.removeClass("fa-search-plus")
					i.addClass("fa-search-minus")
				}
				if ($(".panel-body-search").is(":visible") == false) {
					$(".panel-body-search").show();
				} else {
					$(".panel-body-search").hide();
				}
			});

			$("#resetButton").on("click", function() {
				$("#searchForm>input[type='text']").val('');
			});
			
		});
	</script>
</body>
</html>
