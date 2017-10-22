<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>
        文件柜服务详情
    </title>
</head>

<body>

	<!-- Content Header -->
	<section class="content-header">
		<h1>文件柜服务详情</h1>
	</section>

	<div class="form-horizontal">
		 <!-- part3 -->
		 <section class="content">
				<div class="box box-noborder">
					<div class="box-body">
						<dl class="dl-horizontal">
							<dt>联络人姓名</dt>
							<dd>${boxServiceForm.contactName}</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>联络人电话</dt>
							<dd>${boxServiceForm.contactPhone}</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>联络人邮箱</dt>
							<dd>${boxServiceForm.contactEmail}</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>联络地址</dt>
							<dd>${boxServiceForm.areaName}${boxServiceForm.address}</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>产品类型</dt>
							<dd>${boxServiceForm.productName}</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>产品规格</dt>
							<dd>${boxServiceForm.scaleName}</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>期限</dt>
							<dd>
								<c:forEach var="cycle" items="${cycles}">
									<c:if test="${cycle.key eq boxServiceForm.cycle}">
										${cycle.value}
									</c:if>
								</c:forEach>
							</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>服务柜状态</dt>
							<dd>
								<c:forEach var="flag" items="${flags}">
								<c:if test="${flag.key eq boxServiceForm.flag}">
									${flag.value}
								</c:if>
							</c:forEach>
							</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>服务期间</dt>
							<dd>${boxServiceForm.startTime} - ${boxServiceForm.endTime}</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>服务状态</dt>
							<dd>
								<c:forEach var="status" items="${statuses}">
									<c:if test="${status.key eq boxServiceForm.status}">
										${status.value}
									</c:if>
								</c:forEach>
							</dd>
						</dl>
					</div><!-- /.box-body -->
					<div class="box-footer">
						<button class="btn btn-primary btn-sm disabled btn-70" onclick="window.location.href='${ctx}/boxService'">
							返回
						</button>
					</div>
				</div><!-- /.box -->
		 </section><!-- /part3 -->
	</div>

	<section class="content">
		<div class="box" style="top: -30px">
			<div class="box-body">
				<div class="table-responsive">
					<table class="table table-striped table-hover dataTable" style="margin-bottom:0px;">
						<thead>
						<tr>
							<th>预约时间</th>
							<th>额外费用</th>
							<th>申请时间</th>
							<th>状态</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach var="record" items="${records}" varStatus="index">
							<tr class="${index.count%2==0?'odd':'even'}" id="scale_column_${scale.id}">
								<td>${record.appointmentTime}</td>
								<td>${record.cost}</td>
								<td>${record.createTime}</td>
								<td>${record.status}</td>
							</tr>
						</c:forEach>
						<c:if test="${fn:length(records) == 0}">
							<tr>
								<td colspan="4" class="text-center">没有记录</td>
							</tr>
						</c:if>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</section>

	<script type="text/javascript">
		$(function() {
			$("#menu_client").addClass("active");
		});
	</script>
</body>
</html>



