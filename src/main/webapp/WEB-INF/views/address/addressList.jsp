<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>取货地址列表</title>
</head>
<body>
	<br />
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			<strong>${message}</strong>
		</div>
	</c:if>

	<form id="searchForm" action="${ctx}/address" method="get">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-info">
					<div class="panel-heading">
						取货地址列表
						<small></small>
						<button type="button" class="btn-mini btn-link pull-right search-plus-minus">
							<i class="fa fa-search-minus"></i>
						</button>
					</div>
					<div class="panel-body panel-body-search">
						<div class="row">
							<div class="col-lg-4">
								<div class="form-group">
									<label for="areaId">联系人姓名</label>
									<input type="text" class="form-control" id="contactName" name="contactName" value="<c:out value="${pages.searchMap['contactName']}"/>"
										   placeholder="输入关键字...">
								</div>
							</div>
							<div class="col-lg-4">
								<div class="form-group">
									<label for="areaId">区域</label>
									<tags:selectbox name="areaId" map="${areas}" value="${pages.searchMap['areaId']}" empty="true"/>
								</div>
							</div>
							<div class="col-lg-4">
								<div class="form-group">
									<label for="address">地址</label>
									<input type="text" class="form-control" id="address" name="address" value="<c:out value="${pages.searchMap['address']}"/>"
										   placeholder="输入关键字...">
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
									<th>联系人</th>
									<th <tags:sort column="areaId" page="${pages}"/>>区域</th>
									<th>地址</th>
									<th><spring:message code="public.oper" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pages.content}" var="address" varStatus="index">
									<tr class="${index.count%2==0?'odd':'even'}">
										<td>${address.contactName}</td>
										<td>
											<c:forEach var="area" items="${areas}">
												<c:if test="${area.key eq address.areaId}">
													${area.value}
												</c:if>
											</c:forEach>
										</td>
										<td>${address.address}</td>
										<td>
											<a href="${ctx}/address/update/${address.id}"><i class="fa fa-edit fa-fw"></i></a>
											<a onclick="delcfm('${ctx}/address/delete/${address.id}');"><i class="fa fa-times fa-fw"></i></a>
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

			$("#areaId").select2({
				placeholder: "Please select...",
				allowClear: true,
				language: lang_options(1)
			});
		});
	</script>
</body>
</html>
