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
    <title><spring:message code="service.view.title"/></title>
</head>

<body>

	<div role="form"class="form-horizontal">
		 <section class="content">
			<div class="box box-info">
				<div class="box-header"><h3 class="box-title"><spring:message code="service.view.title"/></h3></div>
				<div class="box-body">
					<div class="row">
						<div class="col-md-8">
							<dl class="dl-horizontal">
								<dt><spring:message code="service.view.contactName"/></dt>
								<dd>${boxServiceForm.contactName}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt><spring:message code="service.view.contactPhone"/></dt>
								<dd>${boxServiceForm.contactPhone}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt><spring:message code="service.view.contactEmail"/></dt>
								<dd>${boxServiceForm.contactEmail}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt><spring:message code="service.view.contactAddress"/></dt>
								<dd>${boxServiceForm.areaName}${boxServiceForm.address}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt><spring:message code="service.view.productId"/></dt>
								<dd>${boxServiceForm.productName}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt><spring:message code="service.view.scale"/></dt>
								<dd>${boxServiceForm.scaleName}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt><spring:message code="service.view.cycle"/></dt>
								<dd>
									<c:forEach var="cycle" items="${cycles}">
										<c:if test="${cycle.key eq boxServiceForm.cycle}">
											${cycle.value}
										</c:if>
									</c:forEach>
								</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt><spring:message code="service.view.useStatus"/></dt>
								<dd>
									<c:forEach var="flag" items="${flags}">
									<c:if test="${flag.key eq boxServiceForm.flag}">
										${flag.value}
									</c:if>
								</c:forEach>
								</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt><spring:message code="service.view.servicePeriod"/></dt>
								<dd>${boxServiceForm.startTime} - ${boxServiceForm.endTime}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt><spring:message code="service.view.belongNo"/></dt>
								<dd>${boxServiceForm.belongNo}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt><spring:message code="service.view.status"/></dt>
								<dd>
									<c:forEach var="status" items="${statuses}">
										<c:if test="${status.key eq boxServiceForm.status}">
											<label class="label label-info">${status.value}</label>
										</c:if>
									</c:forEach>
								</dd>
							</dl>
						</div>
					</div>
				</div>
			</div><!-- /.box -->
		 </section><!-- /part3 -->
	</div>

	<section class="content">
		<div class="box" style="top: -30px">
			<div class="box-header"><h3 class="box-title"><spring:message code="record.view.title"/></h3></div>
			<div class="box-body">
				<div class="row">
					<div class="col-md-9">
						<c:if test="${fn:length(records) > 0}">
						<form action="${ctx}/boxService/record/done" method="post" class="form-horizontal" id="boxRecordForm">
						<input type="hidden" name="id" value="${records[0].id}">
						<input type="hidden" name="picture1" id="picture1" value="${records[0].picture1}">
						<input type="hidden" name="picture2" id="picture2" value="${records[0].picture2}">
						<input type="hidden" name="picture3" id="picture3" value="${records[0].picture3}">
						<dl class="dl-horizontal">
							<dt><spring:message code="record.view.appointmentTime"/></dt>
							<dd>${records[0].appointmentTime}</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt><spring:message code="record.view.serviceFee"/></dt>
							<dd>${records[0].cost}</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt><spring:message code="record.view.createTime"/></dt>
							<dd>${records[0].createTime}</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt><spring:message code="record.view.status"/></dt>
							<dd>
								<c:if test="${records[0].status eq 1}">

									<select name="status" class="form-control status" id="status">
										<c:if test="${records[0].type eq 1}">
											<option value="1" <c:if test="${records[0].status eq 1}">selected</c:if>><spring:message code="record.type1.status1"/></option>
											<option value="2" <c:if test="${records[0].status eq 2}">selected</c:if>><spring:message code="record.type1.status2"/></option>
										</c:if>
										<c:if test="${records[0].type eq 2}">
											<option value="1" <c:if test="${records[0].status eq 1}">selected</c:if> ><spring:message code="record.type2.status1"/></option>
											<option value="2" <c:if test="${records[0].status eq 2}">selected</c:if>><spring:message code="record.type2.status2"/></option>
										</c:if>
									</select>
								</c:if>
								<c:if test="${records[0].status ne 1}">
									<label class="label label-info">
									<c:if test="${records[0].type eq 1}">
										<spring:message code="record.type1.status2"/>
									</c:if>
									<c:if test="${records[0].type eq 2}">
										<spring:message code="record.type2.status2"/>
									</c:if>
									</label>
								</c:if>
							</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt><spring:message code="record.view.picture"/></dt>
							<dd>
								<c:if test="${records[0].status eq 2}">
									<c:if test="${records[0].picture1 != null and records[0].picture1 != ''}">
										<img src="${ctx}/file/dumpImage?path=${records[0].picture1}" class="col-md-3" />
									</c:if>
									<c:if test="${records[0].picture2 != null and records[0].picture2 != ''}">
										<img src="${ctx}/file/dumpImage?path=${records[0].picture2}" class="col-md-3" />
									</c:if>
									<c:if test="${records[0].picture3 != null and records[0].picture3 != ''}">
										<img src="${ctx}/file/dumpImage?path=${records[0].picture3}" class="col-md-3" />
									</c:if>
								</c:if>
								<c:if test="${records[0].status eq 1}">
									<div id="zyupload" class="zyupload"></div>
									<p class="help-block"><i class="fa fa-fw fa-commenting"></i><spring:message code="public.image.tips"/></p>
								</c:if>
							</dd>
						</dl>
						</form><!--./form-->
						</c:if>
						<c:if test="${fn:length(records) == 0}">
							<div class="box-comment text-center"><spring:message code="record.view.noRecord"/></div>
						</c:if>
					</div>
				</div>
			</div>
			<div class="box-footer">
				<c:if test="${fn:length(records) > 0}">
					<c:if test="${records[0].status eq 1}">
					<button class="btn btn-success btn-70" onclick="submitBoxRecordForm();">
						<spring:message code="public.confirm"/>
					</button>
					</c:if>
				</c:if>
				<button class="btn btn-primary disabled btn-70" onclick="window.location.href='${ctx}/boxService'">
					<spring:message code="public.back"/>
				</button>
			</div>
		</div>
	</section>

	<script type="text/javascript">
		$(function() {
			$("#menu_client").addClass("active");

			// 初始化插件
			$("#zyupload").zyUpload({
				width            :   "auto",                 // 宽度
				height           :   "auto",                 // 宽度
				itemWidth        :   "100px",                 // 文件项的宽度
				itemHeight       :   "100px",                 // 文件项的高度
				url              :   "${ctx}/file/image/upload",  // 上传文件的路径
				fileType         :   ["jpg","png"],// 上传文件的类型
				fileSize         :   2097152,                // 上传文件的大小 2m
				multiple         :   false,                    // 是否可以多个文件上传
				dragDrop         :   false,                   // 是否可以拖动上传文件
				tailor           :   false,                   // 是否可以裁剪图片
				del              :   true,                    // 是否可以删除文件
				finishDel        :   false,  				  // 是否在上传文件完成后删除预览
				/* 外部获得的回调接口 */
				onSelect: function(selectFiles, allFiles){    // 选择文件的回调方法  selectFile:当前选中的文件  allFiles:还没上传的全部文件
					console.info("当前选择了以下文件：");
					console.info(selectFiles);
				},
				onDelete: function(file, files){              // 删除一个文件的回调方法 file:当前删除的文件  files:删除之后的文件
					console.info("当前删除了此文件："+file.name);
					$("#uploadList_"+file.index).attr("flag", "remove").removeAttr("url");
				},
				onSuccess: function(file, response){          // 文件上传成功的回调方法
					console.info("此文件上传成功："+file.name);
					console.info("此文件上传到服务器返回："+response);
					var result = eval("("+response+")");
					if(result.success != 'true'){
						alert(result.message);
						return;
					}
					$("#uploadList_"+file.index).attr("flag", "success").attr("data-url", result.fileUrl);
				},
				onFailure: function(file, response){          // 文件上传失败的回调方法
					console.info("此文件上传失败："+file.name);
					console.info("response:"+response);
				},
				onComplete: function(response){           	  // 上传完成的回调方法
					console.info("文件上传完成");
				}
			});

		});

		function submitBoxRecordForm(){
			var flag = false;
			$("div[id^=uploadList_]").each(function(i){
				if($(this).attr('flag') == 'success'){
					$("#picture" + (i+1)).val($(this).attr('data-url'));
					flag = true;
				}
			});
			if(flag==false){
				if(confirm("<spring:message code="record.view.confirmSubmit.tips" />")){
					$("#boxRecordForm").submit();
				}
			}else{
				$("#boxRecordForm").submit();
			}
		}
	</script>
</body>
</html>



