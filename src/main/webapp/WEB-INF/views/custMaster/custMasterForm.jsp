<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title><spring:message code="user.title"/></title>
</head>

<body>
	<div class="row">
	    <div class="col-lg-12">
	        <h1 class="page-header"><spring:message code="custom.manage"/></h1>
	    </div>
	    <!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
	    <div class="col-lg-12">
	        <div class="panel panel-default">
	            <div class="panel-body">
	                <div class="row">
	                    <div class="col-lg-12">
	                        <form role="form" id="inputForm" action="${ctx}/custMaster/${action}" method="post">
	                        	<input type="hidden" name="id" value="${custMaster.id}"/>
	                        	<c:if test="${not empty message}">
	                        		<div class="alert alert-success alert-dismissable">
	                        			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
	                        			${ message }
	                        		</div>
	                        	</c:if>
	                        	
	                        	<fieldset>
	                        		<div class="form-group col-lg-6">
		                                <label><spring:message code="custom.custUsername"/>:</label>
	                       				<input class="form-control" type="text" id="custUsername" name="custUsername" value="${custMaster.custUsername}">
		                            </div>
		                            <div class="form-group col-lg-6">
		                                <label><spring:message code="custom.custPort"/>:</label>
	                       				<input class="form-control" type="text" id="custPort" name="custPort" value="${custMaster.custPort}">
		                            </div>
		                            <div class="form-group col-lg-6">
		                                <label><spring:message code="custom.acctCreateDate"/>:</label>
		                                <div class="input-group date form_date" data-date-format="dd MM yyyy" data-link-format="yyyy-mm-dd">
		                                	<input type="text" value="<fmt:formatDate value="${custMaster.acctCreateDate}" pattern="yyyy-MM-dd"/>" 
		                                	readonly class="form-control" id="acctCreateDate" class="acctCreateDate" />
		                          			<span class="input-group-addon"><span class="glyphicon glyphicon-time"></span>
		                                </div>
		                            </div>
		                            <div class="form-group col-lg-6">
		                                <label><spring:message code="custom.annualSvcFeeDate"/>:</label>
		                                <div class="input-group date form_date" data-date-format="dd MM yyyy" data-link-format="yyyy-mm-dd">
		                                	<input type="text" value="<fmt:formatDate value="${custMaster.annualSvcFeeDate}" pattern="yyyy-MM-dd"/>" 
		                                	readonly class="form-control" id="annualSvcFeeDate" class="annualSvcFeeDate" />
		                          			<span class="input-group-addon"><span class="glyphicon glyphicon-time"></span>
		                                </div>
		                            </div>
		                            <div class="form-group col-lg-6">
		                                <label><spring:message code="custom.custName"/>:</label>
	                       				<input class="form-control" type="text" id="custName" name="custName" value="${custMaster.custName}">
		                            </div>
		                            <div class="form-group col-lg-6">
		                                <label><spring:message code="custom.webName"/>:</label>
	                       				<input class="form-control" type="text" id="webName" name="webName" value="${custMaster.webName}">
		                            </div>
		                            <div class="form-group col-lg-6">
		                                <label><spring:message code="custom.advertiser"/>:</label>
	                       				<input class="form-control" type="text" id="advertiser" name="advertiser" value="${custMaster.advertiser}">
		                            </div>
		                            <div class="form-group col-lg-6">
		                                <label><spring:message code="custom.annualSvcFee"/>:</label>
	                       				<div class="form-group input-group">
	                                         <span class="input-group-addon">￥</span>
	                                         <input type="text" class="form-control" id="annualSvcFee" name="annualSvcFee" value="${custMaster.annualSvcFee}" />
	                                    </div>
		                            </div>
		                            <div class="form-group col-lg-6">
		                                <label><spring:message code="custom.rewardsPercent"/>:</label>
	                       				<input class="form-control" type="text" id="rewardsPercent" name="rewardsPercent" value="${custMaster.rewardsPercent}">
		                            </div>
		                            <div class="form-group col-lg-6">
		                                <label><spring:message code="custom.mgtFeePercent"/>:</label>
	                       				<input class="form-control" type="text" id="mgtFeePercent" name="mgtFeePercent" value="${custMaster.mgtFeePercent}">
		                            </div>
		                        
		                            <div class="form-group col-lg-6">
		                            	 <label><spring:message code="custom.am_contact"/>:</label>
		                            	 <input class="form-control" type="text" id="am_contact" name="am_contact" value="${custMaster.am_contact}">
		                            </div>
		                            <div class="form-group col-lg-6">
		                            	 <label><spring:message code="custom.am_email"/>:</label>
		                            	 <div class="form-group input-group">
		                            	 	<span class="input-group-addon glyphicon-envelope"></span>
		                                	<input class="form-control" type="text" id="am_email" name="am_email" value="${custMaster.am_email}">
		                                 </div>
		                            </div>
		                            <div class="form-group col-lg-6">
		                            	 <label><spring:message code="custom.fin_contact"/>:</label>
		                            	 <input class="form-control" type="text" id="fin_contact" name="fin_contact" value="${custMaster.fin_contact}">
		                            </div>
		                            <div class="form-group col-lg-6">
		                            	 <label><spring:message code="custom.fin_email"/>:</label>
		                            	 <div class="form-group input-group">
			                            	 <span class="input-group-addon glyphicon-envelope"></span>
			                            	 <input class="form-control" type="text" id="fin_email" name="fin_email" value="${custMaster.fin_email}">
		                            	 </div>
		                            </div>
		                            <div class="form-group col-lg-6">
		                            	 <label><spring:message code="custom.ops_contact"/>:</label>
		                            	 <input class="form-control" type="text" id="ops_contact" name="ops_contact" value="${custMaster.ops_contact}">
		                            </div>
		                            <div class="form-group col-lg-6">
		                            	 <label><spring:message code="custom.ops_email"/>:</label>
		                            	 <div class="form-group input-group">
			                            	 <span class="input-group-addon glyphicon-envelope"></span>
			                            	 <input class="form-control" type="text" id="ops_email" name="ops_email" value="${custMaster.ops_email}">
		                            	 </div>
		                            </div>
		                            <div class="form-group col-lg-6">
		                            	 <label><spring:message code="custom.sales_contact"/>:</label>
		                            	 <input class="form-control" type="text" id="sales_contact" name="sales_contact" value="${custMaster.sales_contact}">
		                            </div>
		                            <div class="form-group col-lg-6">
		                            	 <label><spring:message code="custom.sales_email"/>:</label>
		                            	 <div class="form-group input-group">
			                            	 <span class="input-group-addon glyphicon-envelope"></span>
			                            	 <input class="form-control" type="text" id="sales_email" name="sales_email" value="${custMaster.sales_email}">
		                            	 </div>
		                            </div>
		                            <div class="form-group col-lg-6">
		                                <label><spring:message code="custom.remark1"/>:</label>
		                                <textarea class="form-control" rows="3" id="remark1" name="remark1">${custMaster.remark1}</textarea>
	                           		</div>
	                        	</fieldset>
	                        	
	                            <button id="submit_btn" type="submit" class="btn btn-success"><spring:message code="public.commit" /></button>
                            </form>
                        </div>
                   </div>
               </div>
           </div>
       </div>
   </div>
	<script>
		$(document).ready(function() {
			$("#inputForm").validate({
				rules: {
					custUsername:{
						required: true
					},
					annualSvcFee:{
						number:true
					},
					am_email:{
						email:true
					},
					fin_email:{
						email:true
					},
					ops_email:{
						email:true
					},
					sales_email:{
						email:true
					}
						
			    }
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
	</script>
</body>
</html>
