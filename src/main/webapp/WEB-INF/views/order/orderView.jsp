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
    <title><spring:message code="order.view.title" /></title>
</head>

<body>

	<div role="form"class="form-horizontal">
		 <section class="content">
			 <div class="box box-info">
				 <div class="box-header"><h3 class="box-title"><spring:message code="order.view.title" /></h3></div>
				 <div class="box-body">
					<div class="row">
						<div class="col-md-8">
							<dl class="dl-horizontal">
								<dt><spring:message code="order.view.orderNo" /></dt>
								<dd>${orderForm.orderNo}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt><spring:message code="order.view.productId" /></dt>
								<dd>${orderForm.productName}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt><spring:message code="order.view.scale" /></dt>
								<dd>${orderForm.scaleName}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt><spring:message code="order.view.cycle" /></dt>
								<dd>
									<c:forEach var="cycle" items="${cycles}">
										<c:if test="${cycle.key eq orderForm.cycle}">
											${cycle.value}
										</c:if>
									</c:forEach>
								</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt><spring:message code="order.view.totalPrice" /></dt>
								<dd>$ ${orderForm.totalPrice}HKD</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt><spring:message code="order.view.orderTime" /></dt>
								<dd>${orderForm.orderTime}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt><spring:message code="order.view.status" /></dt>
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
						</div>
					</div><!-- /.box-body -->
				 </div>
				 <div class="box-footer">
					 <button class="btn btn-primary disabled btn-70" onclick="window.location.href='${ctx}/order'">
						 <spring:message code="public.back"/>
					 </button>
				 </div>
			</div>
		 </section>
	</div>

	<script type="text/javascript">
		$(function() {
			$("#menu_client").addClass("active");
		});
	</script>
</body>
</html>



