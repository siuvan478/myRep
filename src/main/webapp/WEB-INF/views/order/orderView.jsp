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
        订单详情
    </title>
</head>

<body>

<%--<style>--%>
	<%--.box{}--%>
	<%--.box-noborder{border-top: 0}--%>
	<%--.btn-100{width: 100px;line-height: 1.1}--%>
	<%--.products{padding: 0 15px 0 15px;}--%>
	<%--.content{padding-bottom: 0;min-height:0}--%>
<%--</style>--%>

	<!-- Content Header -->
	<section class="content-header">
		<h1>订单详情</h1>
	</section>

	<div class="form-horizontal">
		 <!-- part3 -->
		 <section class="content">
				<div class="box box-noborder">
					<div class="box-body">
						<dl class="dl-horizontal">
							<dt>订单编号</dt>
							<dd>${orderForm.orderNo}</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>产品类型</dt>
							<dd>${orderForm.productName}</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>产品规格</dt>
							<dd>${orderForm.scaleName}</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>期限</dt>
							<dd>
								<c:forEach var="cycle" items="${cycles}">
									<c:if test="${cycle.key eq orderForm.cycle}">
										${cycle.value}
									</c:if>
								</c:forEach>
							</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>金额</dt>
							<dd>$ ${orderForm.totalPrice}HKD</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>下单时间</dt>
							<dd>${orderForm.orderTime}</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>订单状态</dt>
							<dd>
								<c:forEach var="status" items="${statuses}">
									<c:if test="${status.key eq orderForm.status}">
										<span class="label ${orderForm.labelClass}">${status.value}</span>
										<c:if test="${orderForm.status eq 2}">
											(${orderForm.effectiveTime})
										</c:if>
									</c:if>
								</c:forEach>
							</dd>
						</dl>
					</div><!-- /.box-body -->
					<div class="box-footer">
						<button class="btn btn-primary btn-sm disabled btn-70" onclick="window.location.href='${ctx}/order'">
							返回
						</button>
					</div>
				</div><!-- /.box -->
		 </section><!-- /part3 -->
	</div>

	<script type="text/javascript">
		$(function() {
			$("#menu_client").addClass("active");
		});
	</script>
</body>
</html>



