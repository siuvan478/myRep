<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>订单列表</title>
</head>
<body>
	<br />
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<c:if test="${not empty error_message}">
		<div id="message" class="alert alert-error">
			<button data-dismiss="alert" class="close">×</button>${error_message}</div>
	</c:if>
	<form id="searchForm" action="${ctx}/order" method="get">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-info">
					<div class="panel-heading">
						订单列表
						<small></small>
						<button type="button" class="btn-mini btn-link pull-right search-plus-minus">
							<i class="fa fa-search-minus"></i>
						</button>
					</div>
					<div class="panel-body panel-body-search">
						<div class="row">
							<div class="col-lg-4">
								<div class="form-group">
									<label for="orderNo">订单编号</label>
									<input type="text" class="form-control" id="orderNo" name="orderNo" value="<c:out value="${pages.searchMap['orderNo']}"/>"
										   placeholder="Please enter order number...">
								</div>
							</div>
							<div class="col-lg-4">
								<div class="form-group">
									<label for="productId">产品类型</label>
									<tags:selectbox name="productId" map="${products}" value="${pages.searchMap['productId']}" />
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
									<th <tags:sort column="orderNo" page="${pages}"/>>订单编号</th>
									<th <tags:sort column="productId" page="${pages}"/>>产品类型</th>.
									<th <tags:sort column="scaleId" page="${pages}"/>>产品规格</th>
									<th <tags:sort column="cycle" page="${pages}"/>>周期</th>
									<th <tags:sort column="totalPrice" page="${pages}"/>>金额</th>
									<th <tags:sort column="orderTime" page="${pages}"/>>下单时间</th>
									<th <tags:sort column="status" page="${pages}"/>>状态</th>
									<th><spring:message code="public.oper" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pages.content}" var="order" varStatus="index">
									<tr class="${index.count%2==0?'odd':'even'}">
										<td>${order.orderNo}</td>
										<td>${order.productName}</td>
										<td>${order.scaleName}</td>
										<td>
											<c:if test="${order.cycle eq 1}">1个月</c:if>
											<c:if test="${order.cycle eq 2}">3个月</c:if>
											<c:if test="${order.cycle eq 3}">6个月</c:if>
											<c:if test="${order.cycle eq 4}">12个月</c:if>
										</td>
										<td>$ ${order.totalPrice}HKD</td>
										<td><fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd HH:ss" /></td>
										<td>
											<c:if test="${order.status eq 0}"><span class="label label-default">已取消</span></c:if>
											<c:if test="${order.status eq 1}"><span class="label label-success">新订单</span></td></c:if>
											<c:if test="${order.status eq 2}"><span class="label label-info ">已生效</span></c:if>
										</td>
										<td>
											<a href="${ctx}/order/update/${order.id}" title="查看"><i class="fa fa-eye fa-fw"></i></a>
											<a onclick="showAuditOrderForm('${order.id}');" class="disabled" title="审批" data-toggle="modal" data-target="#auditOrderForm"><i class="fa fa-pencil fa-fw"></i></a>
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

	<!-- audit order form modal -->
	<div class="modal fade" id="auditOrderForm">
	</div>
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

			$("#productId").select2({
				placeholder: "Please enter product name...",
				allowClear: true,
				language: lang_options(1)
			});
			$("#resetButton").on("click", function() {
				$("#searchForm>input[type='text']").val('');
			});
		});

		function showAuditOrderForm(id){
			$.ajax({
				type: 'GET',
				url: '${ctx}/order/audit/'+id,
				dataType: 'html',
				success : function(html){
					console.log(html);
					$("#auditOrderForm").html(html);
				}
			});
		}
	</script>
</body>
</html>
