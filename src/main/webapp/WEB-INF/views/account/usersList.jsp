<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title><spring:message code="user.list.header.title" /></title>
</head>
<body>
	<br />
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<form id="searchForm" action="${ctx}/user" method="get">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-info">
					<div class="panel-heading">
						<spring:message code="user.list.header.title" />
						<small></small>
						<button type="button" class="btn-mini btn-link pull-right search-plus-minus">
							<i class="fa fa-search-minus"></i>
						</button>
					</div>
					<div class="panel-body panel-body-search">
						<div class="row">
							<div class="col-lg-4">
								<div class="form-group">
									<label for="name"><spring:message code="user.list.header.name" /></label>
									<input type="text" class="form-control" id="name" name="name" value="<c:out value="${pages.searchMap['name']}"/>"
										   placeholder="<spring:message code="user.list.header.name" />">
								</div>
							</div>
							<div class="col-lg-4">
								<div class="form-group">
									<label for="loginName"><spring:message code="user.list.header.loginName" /></label>
									<input type="text" class="form-control" id="loginName" name="loginName" value="<c:out value="${pages.searchMap['loginName']}"/>"
										   placeholder="<spring:message code="user.list.header.loginName" />">
								</div>
							</div>
							<div class="col-lg-4">
								<div class="form-group">
									<label for="email"><spring:message code="user.list.header.email" /></label>
									<input type="text" class="form-control" id="email" name="email" value="<c:out value="${pages.searchMap['email']}"/>"
										   placeholder="<spring:message code="user.list.header.email" />">
								</div>
							</div>
							<div class="col-lg-4">
								<div class="form-group">
									<label for="email"><spring:message code="user.list.header.phone" /></label>
									<input type="text" class="form-control" id="phone" name="phone" value="<c:out value="${pages.searchMap['phone']}"/>"
										   placeholder="<spring:message code="user.list.header.phone" />">
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

						<a class="btn btn-primary pull-right" href="${ctx}/user/create">
							<i class="fa fa-user-plus"></i> <spring:message code="public.create" /></a>
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
						<table class="table table-striped  table-hover dataTable" style="margin-bottom:0px;">
							<thead>
								<tr>
									<th <tags:sort column="name" page="${pages}"/>><spring:message code="user.list.body.name" /></th>
									<th <tags:sort column="login_name" page="${pages}"/>><spring:message code="user.list.body.loginName" /></th>
									<th><spring:message code="user.list.body.email" /></th>
									<th><spring:message code="user.list.body.phone" /></th>
									<th><spring:message code="user.list.body.registerTime" /></th>
									<th><spring:message code="public.oper" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pages.content}" var="user" varStatus="index">
									<tr class="${index.count%2==0?'odd':'even'}">
										<td>${user.name}</td>
										<td>${user.loginName}</td>
										<td>${user.email}</td>
										<td>${user.phone}</td>
										<td>${user.registerDate}</td>
										<td>
											<a href="${ctx}/user/update/${user.id}"><i class="fa fa-edit fa-fw"></i></a>
											<c:if test="${user.id ne userId}">
												<a href="javascript:if(confirm('delete?'))window.location.href='${ctx}/user/delete/${user.id}'"><i class="fa fa-times fa-fw"></i></a>
											</c:if>
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
		});
	</script>
</body>
</html>
