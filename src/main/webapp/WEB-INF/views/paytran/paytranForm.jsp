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

    <title><spring:message code="paytran.title"/></title>

	<style type="text/css">
		table th, td{white-space:nowrap;word-break: keep-all;}
	</style>
    <!-- Bootstrap Core CSS -->
    <link href="${ctx}/static/startbootstrap-sb-admin-2-1.0.7/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="${ctx}/static/startbootstrap-sb-admin-2-1.0.7/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Timeline CSS -->
    <link href="${ctx}/static/startbootstrap-sb-admin-2-1.0.7/dist/css/timeline.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="${ctx}/static/startbootstrap-sb-admin-2-1.0.7/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="${ctx}/static/startbootstrap-sb-admin-2-1.0.7/bower_components/morrisjs/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="${ctx}/static/startbootstrap-sb-admin-2-1.0.7/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

	<link href="${ctx}/static/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet" />
	
	<!-- datetimepicker -->
    <link href="${ctx}/static/bootstrap/3.3.5/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
	

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
      <!-- jQuery -->
    <script src="${ctx}/static/startbootstrap-sb-admin-2-1.0.7/bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="${ctx}/static/startbootstrap-sb-admin-2-1.0.7/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="${ctx}/static/startbootstrap-sb-admin-2-1.0.7/bower_components/metisMenu/dist/metisMenu.min.js"></script>

   <!-- ajaxupload -->
   <script src="${ctx}/static/ajaxupload/ajaxupload.3.6.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="${ctx}/static/startbootstrap-sb-admin-2-1.0.7/dist/js/sb-admin-2.js"></script>
    
    <!-- 
    <script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
     -->
	<script src="${ctx}/static/jquery-validation/1.14.0/dist/jquery.validate.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-validation/1.14.0/dist/jquey.validate.override.js" type="text/javascript"></script>
	
	<!-- datepickerjs -->
	<script src="${ctx}/static/bootstrap/3.3.5/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
	<%
	String lang = request.getLocale().getLanguage();
	if("zh".equals(lang)){
		%>
		<script src="${ctx}/static/jquery-validation/1.14.0/dist/localization/messages_zh.js" type="text/javascript"></script>
		<%
	}
	%>
</head>

<body>
	<div class="container">
	<div class="row">
	    <div class="col-lg-12">
	        <h1 class="page-header"><spring:message code="paytran.edit"/>
	        <c:if test="${action =='update'}">(ID = ${payTranHeader.tranNum})</c:if></h1>
	    </div>
	    <!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
	  <form role="form" id="inputForm" action="${ctx}/paytran/${action}" method="post" enctype="multipart/form-data">
	    <div class="col-lg-12">
	    	<c:if test="${not empty error}">
        		<div class="alert alert-danger alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>${error}</div>
           	</c:if>
	    	<c:if test="${not empty message}">
        		<div class="alert alert-success alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
        		${message}
        		<br/>
        		${message2}
        		</div>
           	</c:if>
	        <div class="panel panel-default">
	            <div class="panel-body">
	                <div class="row">
	                
	                    <input type="hidden" name="tranNum" value="${payTranHeader.tranNum}"/>
	                    
                        <div class="col-lg-6">
                        		<div class="form-group div-currencys">
	                                <label for="currency"><spring:message code="paytran.currency" />:</label>
                       				<tags:selectbox name="currency" map="${payTranHeader.currencys}" value="${payTranHeader.currency}"></tags:selectbox>
	                            </div>
                        </div>
                        <div class="col-lg-6">
                        		<div class="form-group">
	                                <label for="email"><spring:message code="paytran.email" />:</label>
                       				<input class="form-control" type="text" id="email" name="email" value="${payTranHeader.email}">
	                            </div>
                        </div>
                        <div class="col-lg-6">
                        		<div class="form-group">
	                                <label for="remarks"><spring:message code="paytran.remarks" />:</label>
                       				<textarea rows="3" class="form-control" id="remarks" name="remarks">${payTranHeader.remarks}</textarea>
	                            </div>
                        </div>
                        <div class="col-lg-6">
                        		<div class="form-group">
	                                <label for="totalAmount"><spring:message code="paytran.totalamount" />:</label>
                       				<input class="form-control" type="text" id="totalAmount" name="totalAmount" value="${payTranHeader.totalAmount}" readonly>
	                            </div>
                        </div>
                         
                        
                        <div class="col-lg-12">
                        	<div class="form-group">
							    <label for="inputFile"><spring:message code="paytran.attachment"/></label>
							    
							    <div id="filesDIV">
							    	<button type="button" class="btn btn-info pull-left" data-toggle="modal" data-target="#myModal">
								 		 <spring:message code="public.upload"/>
									</button>
									<br/><br/>
									<div id="filesLabel">
										<c:if test="${not empty payTranHeader.payTranAttachements}">
											<c:forEach items="${payTranHeader.payTranAttachements}" var="payTranAttachement" varStatus="status">
												<label>&nbsp;&nbsp;&nbsp;&nbsp;${status.index+1}.&nbsp;${payTranAttachement.showName}<a href="javascript:void(0)" onclick="delAttach(${payTranAttachement.attachmentId},this)"><i class='fa fa-minus-circle'></i></a></label><br/>
											</c:forEach>
										</c:if>
									</div>
									<c:if test="${not empty payTranHeader.payTranAttachements}">
										<c:forEach items="${payTranHeader.payTranAttachements}" var="payTranAttachement">
											<input type='hidden' name='attachments' id='attachment${payTranAttachement.attachmentId}' value='${payTranAttachement.attachmentId}'>
										</c:forEach>
									</c:if>
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
			             	<h3 class="page-header" style="margin-top: 10px;"><spring:message code="paytran.detail.title" /><span id="detailIndex"></span></h3>
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
									<th><spring:message code="public.oper" /></th>
								</tr>
								</thead>
								<tbody>
									<c:if test="${not empty payTranHeader.payTranDetails}">
										<c:forEach items="${payTranHeader.payTranDetails}" var="payTranDetail">
											<tr class="detailTR">
									   			<td><input class="form-control bdUserName" type="text" id="bdUserName" name="bdUserName" value="${payTranDetail.bdUserName}"></td>
									   			<td class="td-payCodes"><tags:selectbox name="payCode" map="${payTranHeader.payCodes}" value="${payTranDetail.payCode}"></tags:selectbox></td>
									   			<td ><input style="text-align: right;" class="form-control amount" type="text" id="amount" name="amount" value="${payTranDetail.amount}"></td>
									   			<td><input  style="text-align: right;" class="form-control amountInRMB" type="text" id="amountInRMB" name="amountInRMB" value="${payTranDetail.amountInRMB}" readonly></td>
									   			<td>
									   				<a href="javascript:void(0)" onclick="delDetailTR(this);">
									   				<i class="fa fa-minus-circle" style="margin-top: 10px;"></i>
									   				</a>
									   				<input type="hidden" class="paytranDetailStr" name="paytranDetailStr">
									   			</td>
								   			</tr>
										</c:forEach>
									</c:if>
									<c:if test="${empty payTranHeader.payTranDetails}">
									<tr class="detailTR">
							   			<td><input class="form-control bdUserName" type="text" id="bdUserName" name="bdUserName" value=""></td>
							   			<td class="td-payCodes"><tags:selectbox name="payCode" map="${payTranHeader.payCodes}" value=""></tags:selectbox></td>
							   			<td><input style="text-align: right;" class="form-control amount" type="text" id="amount" name="amount" value=""></td>
							   			<td><input style="text-align: right;" class="form-control amountInRMB" type="text" id="amountInRMB" name="amountInRMB" value="" readonly></td>
							   			<td>
							   				<a href="javascript:void(0)" onclick="delDetailTR(this);">
							   				<i class="fa fa-minus-circle" style="margin-top: 10px;"></i>
							   				</a>
							   				<input type="hidden" class="paytranDetailStr" name="paytranDetailStr">
							   			</td>
						   			</tr>
									</c:if>
									<tr>
										<td colspan="5"><a href="javascript:void(0)" onclick="addDetailTR(this);"><i class="fa fa-plus-circle"></i></a></td>
									</tr>
								</tbody>
							</table>
						</div>
						</div>
						</div>
						</div>
	                   <!-- paydetails  end -->
	                   <div class="col-lg-12">
                            <div class="form-group input-group">
                             <label for="jcaptchaCode"><spring:message code="index.verificationcode" />:</label><br/>
							 <input type="text" id="jcaptchaCode" name="jcaptchaCode" class="form-control col-sm-6" style="width: 50%">
							 <img class="col-sm-6 jcaptcha-btn jcaptcha-img" style="height: 34px" id="jcaptchaCodeImg" src="${pageContext.request.contextPath}/jcaptcha.jpg" title="<spring:message code='index.verificationcode' />">
                           	</div>
	                   </div>
	                   
	                   <div class="col-lg-12">
		                       <button id="submit_btn" type="button" onclick="submitForm();" class="btn btn-success"><spring:message code="public.commit" /></button>
		               </div>
                   </div>
               </div>
           </div>
       </div>
     </form>
   </div>
   </div>
   <!-- modal -->
	<jsp:include page="upload.jsp"></jsp:include>
   
	<script>
		//用于统计自动生成的tr
		var i = 1;    
	
		$(document).ready(function() {
			
			$(".currency").change(function(){
				$(".detailTR").each(function(){
					var $tr = $(this);
					var $amount = $(this).find("td input.amount");
					$tr.find("td input.amountInRMB").val(moneyChange($(".currency").val(),$amount.val()));
				});
			});
			
			var keyArr = [];
			var valueArr=[];
			<c:forEach items="${currencyRates}" var="mymap" varStatus="status">
				keyArr['${status.index}'] = '${mymap.key}';
				valueArr['${status.index}'] = '${mymap.value}';
			</c:forEach>
			
			function moneyChange(key,money){
				for(var i = 0 ; i < keyArr.length;i++){
					if(keyArr[i] == key){
						return Math.round(money/valueArr[i]*100)/100;
					}
				}
			};
			
			// 汇率转换
			$("tbody").on('keyup','.amount',function(){
				$(this).parent().next().find(".amountInRMB").val(moneyChange($(".currency").val(),$(this).val()));
				calTotalAmount();
			});
			
			// 计算total amount
			function calTotalAmount(){
				var totalAmount = 0;
				$("tbody .detailTR").each(function(){
					var amount = $(this).find("td input.amount").val();
					totalAmount = parseFloat(totalAmount) + parseFloat(amount);
				});
				$("#totalAmount").val(totalAmount);
			};
			
			$(".jcaptcha-btn").click(function() {
	            $(".jcaptcha-img").attr("src", '${pageContext.request.contextPath}/jcaptcha.jpg?'+new Date().getTime());
	        });
			
			// 添加上传按钮
			$("#filesDIV").on('click','.addInputFile',function(){
				var html = "<input type=\"file\"  name=\"files\">";
				html += "<a href=\"javascript:void(0)\" class=\"addInputFile\"><i class=\"fa fa-plus-circle\"></i></a>&nbsp;";
				html += "<a href=\"javascript:void(0)\" class=\"delInputFile\"><i class=\"fa fa-minus-circle\"></i></a>";
				$("#filesDIV").append(html);
			});
			
			$("#filesDIV").on('click','.delInputFile',function(){
				$(this).prev().prev().remove();
				$(this).prev().remove();
				$(this).remove();
			});
			
			$("#inputForm").validate({
				rules: {
					email:{
						required: true,
						email: true
					},
					totalAmount:"number",
					bdUserName:"required",
					amount:{
						required:true,
						number:true
					}
			    }
			});
			
		});
		
		function submitForm(){
			$(".detailTR").each(function(i){
				var bdUserName = $(this).find(".bdUserName").val();
				var payCode = $(this).find(".payCode").val();
				var amount = $(this).find(".amount").val();
				var amountInRMB = $(this).find(".amountInRMB").val();
				$(this).find(".paytranDetailStr").val(bdUserName+"#"+payCode+"#"+amount+"#"+amountInRMB);
			});
			$("#inputForm").submit();
		};
		
		function addDetailTR(name){
			var selHtml = $(".td-payCodes select").html();
			var html="<tr class=\"detailTR\">";
			html+="<td><input class=\"form-control bdUserName\" type=\"text\" id=\"bdUserName"+i+"\" name=\"bdUserName"+i+"\" value=\"\"></td>";
			html+="<td><select class=\"form-control payCode\" id=\"payCode"+i+"\" name=\"payCode"+i+"\">"+selHtml+"</td>";
			html+="<td><input class=\"form-control amount\" type=\"text\" id=\"amount"+i+"\" name=\"amount"+i+"\" value=\"\"></td>";
			html+="<td><input class=\"form-control amountInRMB\" type=\"text\" id=\"amountInRMB"+i+"\" name=\"amountInRMB"+i+"\" value=\"\" readonly></td>";
			html+="<td><a href=\"javascript:void(0)\" onclick=\"delDetailTR(this);\"><i class=\"fa fa-minus-circle\" style=\"margin-top: 10px;\"></i></a><input type=\"hidden\" class=\"paytranDetailStr\" name=\"paytranDetailStr\"></td></tr>";
			$(name).parent().parent().before(html);
			// 用于动态表单验证
			$("#bdUserName"+i).rules('add', {required: true});
			$("#amount"+i).rules('add', {required: true,number:true});
			i++;
		};
		
		function delDetailTR(name){
			$(name).parent().parent().remove();
		};
		
		function delAttach(attachmentId,name){
			if(confirm('<spring:message code="public.confirmdel"/>?')){
				$.post('${ctx}/ajax/delAttach/'+attachmentId,{},function(data){
					var html = "<div  class=\"alert alert-success\"><button data-dismiss=\"alert\" class=\"close\">×</button>"+data+"</div>";
					$("#filesDIV").append(html);
				},'text');
				$(name).parent().next().remove();
				$(name).parent().remove();
				$("#attachment"+attachmentId).remove();
				$("#filename"+attachmentId).remove();
				$("#uploadImg"+attachmentId).remove();
				syncAttach();
				window.setTimeout(function() { $(".alert-success").alert('close'); }, 3000);
			}
		};
		
		// 同步 form 和 上传页面的数据一致
		function syncAttach(){
			var html = "";
			$("#filesLabel label").each(function(){
				html = html +"<label>"+ $(this).text()+"</label><br/>"
			});
			$("#uploaded").html(html);
		};
		
		
	    window.setTimeout(function() { $(".alert-success").alert('close'); }, 3000);
	</script>

</body>
</html>
