<%@page import="com.asgab.entity.Process"%>
<%@page import="com.asgab.entity.PayTranHeader"%>
<%@page import="java.util.List"%>
<%@page import="com.asgab.util.CommonUtil"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title><spring:message code="paytran.title"/></title>
</head>

<body>
	<div class="row">
	    <div class="col-lg-12">
	        <h3 class="page-header"><spring:message code="paytran.title"/>
	        (Transaction Number : ${payTranHeader.tranNum})</h3>
	    </div>
	    <!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
	  <form role="form"  id="inputForm" action="" method="post">
	    <div class="col-lg-12">
	        <div class="panel panel-default">
	            <div class="panel-body">
	                <div class="row">
                        <div class="col-lg-6">
                        		<div class="form-group">
	                                <label for="currency"><spring:message code="paytran.currency" />:</label>
                       				<span  class="view-span">${payTranHeader.decodedCurrency}</span>
	                            </div>
                        </div>
                        <div class="col-lg-6">
                        		<div class="form-group">
	                                <label for="email"><spring:message code="paytran.email" />:</label>
                       				<span>${payTranHeader.email}</span>
	                            </div>
                        </div>
                        <div class="col-lg-6">
                        		<div class="form-group">
	                                <label for="totalAmount"><spring:message code="paytran.totalamount" />:</label>
                       				<span><fmt:formatNumber value="${payTranHeader.totalAmount}" pattern="#,##0.00#"/></span>
	                            </div>
                        </div>
                        <div class="col-lg-6">
	                        	<input type="hidden" name="tranNum" value="${payTranHeader.tranNum}"/>
	                            <div class="form-group">
	                                <label for="tranDate"><spring:message code="paytran.trandate" />:</label>
	                            	<span class="view-span"><fmt:formatDate value="${payTranHeader.tranDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span>
	                            </div>
                        </div>
                        <div class="col-lg-6">
                         		<div class="form-group">
	                                <label for="status"><spring:message code="paytran.status" />:</label>
                       				<span>${payTranHeader.decodedStatus}</span>
	                            </div>
	                    </div>
                        <div class="col-lg-6">
                        		<div class="form-group">
	                                <label for="remarks"><spring:message code="paytran.remarks" />:</label>
                       				<span>${payTranHeader.remarks}</span>
	                            </div>
                        </div>
                        
                        <div class="col-lg-12">
                        	<div class="form-group">
							    <label for="inputFile"><spring:message code="paytran.attachment"/></label>
							    <div id="uploadedFiles">
							    	<c:forEach items="${payTranHeader.payTranAttachements }" var="attach" varStatus="status">
							    			<label>&nbsp;&nbsp;&nbsp;&nbsp;${status.index+1}.&nbsp;${attach.showName}</label>
							    			<a href="${ctx}/paytran/downloadAttach/${attach.attachmentId}"><i class="fa fa-download"></i></a><br/>
							    	</c:forEach>
							    </div>
						  	</div>
                        </div>
                        
                         <div class="col-lg-12">
                        		<div class="form-group" id="dumpImageDiv">
                        			<c:if test="${not empty payTranHeader.payTranAttachements}">
                        				<c:forEach items="${payTranHeader.payTranAttachements }" var="attach">
                        					<c:if test="${attach.booleanPDF eq true }">
	                        					<img id="uploadImg${attach.attachmentId}" src="${ctx}/static/images/pdf.png" style="height:120px;width:100px;margin-right:10px;" />
                        					</c:if>
                        					<c:if test="${attach.booleanPDF ne true }">
                        						<img id="uploadImg${attach.attachmentId}" src="${ctx}/file/dumpImage?path=${attach.path}/${attach.fileName}" style="height:120px;width:100px;margin-right:10px;" />
                        					</c:if>
                        				</c:forEach>
                        			</c:if>
	                            </div>
                        </div>
                        
                        <div class="col-lg-12">
			             	<h4 class="page-header" style="margin-top: 10px;"><spring:message code="paytran.detail.title" /><span id="detailIndex"></span></h4>
			             </div>
			             
                        <!-- pay details -->
                        <div class="col-lg-12">
                        <div class="panel panel-default">
						<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-striped  table-hover">
	                     		<thead>
								<tr>
									<th><spring:message code="paytran.bdusername" /></th>
									<th><spring:message code="paytran.paycode" /></th>
									<th><spring:message code="paytran.amount" /></th>
									<th><spring:message code="paytran.amountinrmb" /></th>
								</tr>
								</thead>
								<tbody>
									<c:forEach items="${payTranHeader.payTranDetails}" var="payTranDetail">
										<tr class="detailTR">
								   			<td>${payTranDetail.bdUserName}</td>
								   			<td>${payTranDetail.decodePayCode}</td>
								   			<td align="right"><fmt:formatNumber value="${payTranDetail.amount}" pattern="#,##0.00#"/></td>
								   			<td align="right">
								   			<fmt:formatNumber value="${payTranDetail.amountInRMB}" pattern="#,##0.00#"/>
						   				</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						</div>
						</div>
						</div>
	                   <!-- paydetails  end -->
                   </div>
               </div>
           </div>
       </div>
     </form>
   </div>
   
	<script>
	</script>
</body>
</html>
