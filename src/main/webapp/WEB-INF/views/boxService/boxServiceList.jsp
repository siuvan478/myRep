<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>文件柜服务列表</title>
</head>
<body>
	<br />
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<form id="searchForm" action="${ctx}/boxService" method="get">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-info">
					<div class="panel-heading">
						文件柜服务列表
						<small></small>
						<button type="button" class="btn-mini btn-link pull-right search-plus-minus">
							<i class="fa fa-search-minus"></i>
						</button>
					</div>
					<div class="panel-body panel-body-search">
						<div class="row">
							<div class="col-lg-4">
								<div class="form-group">
									<label for="contactName">联络人姓名</label>
									<input type="text" class="form-control" id="contactName" name="contactName" value="<c:out value="${pages.searchMap['contactName']}"/>"
										   placeholder="Please enter contact name...">
								</div>
							</div>
							<div class="col-lg-4">
								<div class="form-group">
									<label for="productId">产品类型</label>
									<tags:selectbox name="productId" map="${products}" value="${pages.searchMap['productId']}" empty="true" />
								</div>
							</div>
						</div>
					</div>
					<div class="panel-footer panel-footer-search">
						<div class="btn-group" role="group" aria-label="...">
							<button type="submit" class="btn  btn-info">
								<i class="fa fa-search"></i> <spring:message code="public.search" />
							</button>
							<button id="resetButton" type="button" class="btn btn-warning" onclick="searchFormReset();">
								<i class="fa fa-repeat"></i> <spring:message code="public.reset" />
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
									<th <tags:sort column="userId" page="${pages}"/>>联络人</th>
									<th <tags:sort column="productId" page="${pages}"/>>产品类型</th>
									<th <tags:sort column="scaleId" page="${pages}"/>>规格</th>
									<th <tags:sort column="cycle" page="${pages}"/>>周期</th>
									<th <tags:sort column="startTime" page="${pages}"/>>服务开始时间</th>
									<th <tags:sort column="endTime" page="${pages}"/>>服务结束时间</th>
									<th <tags:sort column="flag" page="${pages}"/>>服务柜状态</th>
									<th <tags:sort column="status" page="${pages}"/>>状态</th>
									<th><spring:message code="public.oper" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pages.content}" var="boxService" varStatus="index">
									<tr class="${index.count%2==0?'odd':'even'}">
										<td>${boxService.contactName}</td>
										<td>${boxService.productName}</td>
										<td>${boxService.scaleName}</td>
										<td>
											<c:forEach var="cycle" items="${cycles}">
												<c:if test="${cycle.key eq boxService.cycle}">
													${cycle.value}
												</c:if>
											</c:forEach>
										</td>
										<td>${boxService.startTime}</td>
										<td>${boxService.endTime}</td>
										<td>
											<c:forEach var="flag" items="${flags}">
												<c:if test="${flag.key eq boxService.flag}">
													${flag.value}
												</c:if>
											</c:forEach>
										</td>
										<td>
											<c:forEach var="status" items="${statuses}">
											<c:if test="${status.key eq boxService.status}">
												${status.value}
											</c:if>
										</c:forEach>
										</td>
										<td>
											<a href="${ctx}/boxService/view/${boxService.id}"><i class="fa fa-eye fa-fw"></i></a>
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

			$("#productId").select2({
				placeholder: "Please select...",
				allowClear: true,
				language: lang_options(1)
			});
		});
	</script>
</body>
</html>
