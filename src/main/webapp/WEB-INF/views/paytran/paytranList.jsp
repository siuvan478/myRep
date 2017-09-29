<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="com.asgab.service.account.ShiroDbRealm.ShiroUser"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
%>

<html>
<head>
<style type="text/css">
	.color-red{color: red;}
</style>
<title><spring:message code="paytran.title" /></title>
</head>
<body>
	<br/>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>
		${message}
		<c:if test="${not empty message2}"><br/>${message2}</c:if>
		
		</div>
	</c:if>
	<form id="searchForm" action="${ctx}/paytran" method="get">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-info">
					<div class="panel-heading">
						<spring:message code="paytran.title" />
						<small></small>
						<button type="button"
							class="btn-mini btn-link pull-right search-plus-minus">
							<i class="fa fa-search-minus"></i>
						</button>
					</div>
					<div class="panel-body panel-body-search">
						<div class="row">
							<div class="col-lg-4">
								<div class="form-group">
									<label for="tranNum"><spring:message code="paytran.trannum"/></label> <input type="text"
										class="form-control" id="tranNum" name="tranNum"
										value="<c:out value="${pages.searchMap['tranNum']}"/>"
										placeholder="<spring:message code="paytran.trannum"/>">
								</div>
							</div>
							<div class="col-lg-4">
								<div class="form-group">
									<label for="email"><spring:message code="paytran.email"/></label> <input type="text"
										class="form-control" id="email" name="email"
										value="<c:out value="${pages.searchMap['email']}"/>"
										placeholder="<spring:message code="paytran.email"/>">
								</div>
							</div>
							<div class="col-lg-4">
								<div class="form-group">
									<label for="status"><spring:message code="paytran.status"/></label>
									<tags:selectbox name="status" map="${statuses}" value="${pages.searchMap['status']}"></tags:selectbox>
								</div>
							</div>
							<div class="col-lg-4">
								<div class="form-group">
									<label for="tranStartDate"><spring:message code="paytran.transtartdate"/></label> 
									<div class="input-group date form_date" data-date-format="dd MM yyyy" id="datetimepicker" data-link-format="yyyy-mm-dd">
	                            		<input class="form-control" type="text" value="${pages.searchMap['tranStartDate']}" id="tranStartDate" name="tranStartDate"  placeholder="<spring:message code="paytran.transtartdate"/>">
										<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
	                            	</div>
								</div>
							</div>
							<div class="col-lg-4">
								<div class="form-group">
									<label for="tranEndDate"><spring:message code="paytran.tranenddate"/></label> 
									<div class="input-group date form_date" data-date-format="dd MM yyyy" id="datetimepicker" data-link-format="yyyy-mm-dd">
	                            		<input class="form-control" type="text" value="${pages.searchMap['tranEndDate']}" id="tranEndDate" name="tranEndDate"  placeholder="<spring:message code="paytran.tranenddate"/>">
										<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
	                            	</div>
								</div>
							</div>
							
						</div>
					</div>
					<div class="panel-footer panel-footer-search">
						<div class="btn-group" role="group" aria-label="...">
							<button type="submit" class="btn  btn-info"><i class="fa fa-search"></i> <spring:message code="public.search" /></button>
							<button id="resetButtom" type="button" class="btn  btn-warning"><i class="fa fa-repeat"></i> <spring:message code="public.reset" /></button>
						</div>
						<a class="btn  btn-primary pull-right" href="${ctx}/paytran/create"><i class="fa fa-edit"></i> <spring:message code="public.create" /></a>
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
						<table class="table table-striped dataTable table-hover" style="margin-bottom:0px;">
							<thead>
								<tr>
									<th <tags:sort column="trannum" page="${pages}"/>><spring:message code="paytran.trannum" /></th>
									<th <tags:sort column="trandate" page="${pages}"/>><spring:message code="paytran.trandate" /></th>
									<th <tags:sort column="email" page="${pages}"/>><spring:message code="paytran.email" /></th>
									<th <tags:sort column="currency" page="${pages}"/>><spring:message code="paytran.currency" /></th>
									<th <tags:sort column="totalamount" page="${pages}"/>><spring:message code="paytran.totalamount" /></th>
									<th><spring:message code="paytran.status" /></th>
									<th><spring:message code="paytran.remarks" /></th>
									<th><spring:message code="public.oper" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pages.content}" var="paytran" varStatus="index">
									<tr class="${index.count%2==0?'odd':'even'}">
										<td>${paytran.tranNum}</td>
										<td><fmt:formatDate value="${paytran.tranDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										<td>${paytran.email}</td>
										<td>${paytran.decodedCurrency}</td>
										<td align="right"><fmt:formatNumber value="${paytran.totalAmount}" pattern="#,##0.00#"/> </td>
										<td>${paytran.decodedStatus}</td>
										<td>${paytran.remarks}</td>
										<td>
											<a class="color-black" href="javascript:window.location.href='${ctx}/paytran/update/${paytran.tranNum}'"><i class="fa  fa-search"></i></a>
											<c:if test="${paytran.statusStr == '1'}">
												<shiro:hasRole name="finance">
													<a href="javascript:void(0);" onclick="pass(${paytran.tranNum},${paytran.statusStr});">
													<i class="fa fa-check-circle"></i>
													</a>
													<a href="#" data-toggle="modal" onclick="showReject(${paytran.tranNum});">
													<i class="fa fa-times-circle" ></i>
													</a>
												</shiro:hasRole>
											</c:if>
											
											<c:if test="${paytran.statusStr == '3'}">
												<shiro:hasRole name="finance">
													<a href="javascript:void(0);" onclick="pass(${paytran.tranNum},${paytran.statusStr});">
													<i class="fa fa-check-circle"></i>
													</a>
												</shiro:hasRole>
											</c:if>
											
											<c:if test="${paytran.statusStr == '4'}">
												<shiro:hasRole name="ops">
													<a href="javascript:void(0);" onclick="pass(${paytran.tranNum},${paytran.statusStr});">
													<i class="fa fa-check-circle"></i>
													</a>
												</shiro:hasRole>
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
	<jsp:include page="reject.jsp"></jsp:include>
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
			
			$("#searchForm").validate({
				errorPlacement:function(error, element) {
					error.insertAfter(element.prev());
				},
				rules: {
					tranNum:"number"
			    }
			});

			$("#resetButtom").on("click", function() {
				$("#tranNum").val("");
				$("#email").val("");
				$("#tranStartDate").val("");
				$("#tranEndDate").val("");
				$("#status").val("0");
			});
			
			$(".form_date").datetimepicker({
				format: 'yyyy-mm-dd',
				weekStart: 1,
		        todayBtn:  1,
				autoclose: 1,
				todayHighlight: 1,
				startView: 2,
				minView: 2,
				forceParse: 0
			});
		});
		
		function link(url){
			window.location.href=url;
		};
		
		function pass(tranNum,status){
			if(confirm('<spring:message code="paytran.finance.check"/>')){
				link("${ctx}/paytran/pass/"+tranNum+"/"+status);
			}
		};
		
		function showReject(tranNum){
			$("#rejectModal").modal();
			$(".modal-body").find("input").val(tranNum);
		};
	</script>
</body>
</html>
