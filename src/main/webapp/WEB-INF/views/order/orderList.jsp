<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title><spring:message code="order.list.header.title"/></title>
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
						<spring:message code="order.list.header.title"/>
						<small></small>
						<button type="button" class="btn-mini btn-link pull-right search-plus-minus">
							<i class="fa fa-search-minus"></i>
						</button>
					</div>
					<div class="panel-body panel-body-search">
						<div class="row">
							<div class="col-lg-4">
								<div class="form-group">
									<label for="orderNo"><spring:message code="order.list.header.orderNo"/></label>
									<input type="text" class="form-control" id="orderNo" name="orderNo" value="<c:out value="${pages.searchMap['orderNo']}"/>"
										   placeholder="<spring:message code="order.list.header.orderNo"/>">
								</div>
							</div>
							<div class="col-lg-4">
								<div class="form-group">
									<label for="productId"><spring:message code="order.list.header.productId"/></label>
									<tags:selectbox name="productId" map="${products}" value="${pages.searchMap['productId']}" empty="true" />
								</div>
							</div>
							<div class="col-lg-4">
								<div class="form-group">
									<label for="status"><spring:message code="order.list.header.status"/></label>
									<select name="status" class="status form-control" id="status">
										<option value><spring:message code="select2.placeholder"/></option>
										<c:forEach var="status" items="${statuses}">
											<option value="${status.key}" <c:if test="${pages.searchMap['status'] eq status.key}">selected</c:if>>${status.value}</option>
										</c:forEach>
									</select>
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
							<button id="resetButton" type="button" class="btn btn-warning" onclick="searchFormReset();">
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
									<th <tags:sort column="orderNo" page="${pages}"/>><spring:message code="order.list.body.orderNo"/></th>
									<th <tags:sort column="productId" page="${pages}"/>><spring:message code="order.list.body.productId"/></th>
									<th <tags:sort column="scaleId" page="${pages}"/>><spring:message code="order.list.body.scale"/></th>
									<th <tags:sort column="cycle" page="${pages}"/>><spring:message code="order.list.body.cycle"/></th>
									<th <tags:sort column="totalPrice" page="${pages}"/>><spring:message code="order.list.body.totalPrice"/></th>
									<th <tags:sort column="orderTime" page="${pages}"/>><spring:message code="order.list.body.orderTime"/></th>
									<th><spring:message code="order.list.body.addressId"/></th>
									<th <tags:sort column="status" page="${pages}"/>><spring:message code="order.list.body.status"/></th>
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
											<c:forEach var="cycle" items="${cycles}">
												<c:if test="${cycle.key eq order.cycle}">
													${cycle.value}
												</c:if>
											</c:forEach>
										</td>
										<td>$ ${order.totalPrice}HKD</td>
										<td><fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd HH:ss" /></td>
										<td><span class="label label-default" onclick="window.open('${ctx}/address/update/${order.addressId}');" style="cursor: pointer"><spring:message code="order.list.body.addressId.view"/></span></td>
										<td>
											<c:forEach var="status" items="${statuses}">
												<c:if test="${status.key eq order.status}">
													<span class="label ${order.labelClass}">${status.value}</span>
												</c:if>
											</c:forEach>
										</td>
										<td>
											<a href="${ctx}/order/view/${order.id}" title="<spring:message code="order.list.body.view"/>"><i class="fa fa-eye fa-fw"></i></a>
											<c:if test="${order.status eq 1}">
												<a onclick="showAuditOrderForm('${order.id}');" class="disabled" title="<spring:message code="order.list.body.audit"/>" data-toggle="modal" data-target="#auditOrderForm"><i class="fa fa-pencil fa-fw"></i></a>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					
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
				placeholder: "<spring:message code="select2.placeholder"/>",
				allowClear: true,
				language: lang_options(1)
			});
			$("#status").select2();
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
