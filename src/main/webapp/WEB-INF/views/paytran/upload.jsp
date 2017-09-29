<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel"><spring:message code='public.fileupload'/></h4>
			</div>
			<div class="modal-body">
				<div class="progress">
				  <div id="progress-bar" class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;width: 0%;">
				    0%
				  </div>
				</div>
				<div class="file-box">
					<input type="button" class="btn" name="up" id="uploadButton" class="btn" value="<spring:message code='public.upload'/>" />
					<span id='uploading'></span><br/>
					<span id='uploaded'></span>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var intervalId 	;
	var uploadObject = new AjaxUpload('uploadButton', {
		// 文件上传Action
		action : '${ctx}/file/paytranUpload?fileType=common',
		// 名称
		name : 'file',
		// 自动上传
		autoSubmit : true,
		// 返回Text格式数据
		responseType : 'json',
		// 上传处理
		onSubmit : function(filename, ext) {
			//ext是后缀名
			if(ext && /^(jpg|jpeg|png|gif|pdf)$/.test(ext)){
				$('#uploadButton').hide();
				$('#uploading').html("<spring:message code='public.uploading'/>");
				intervalId = setInterval("myInterval()", 200)
			}else{	
				$('#uploading').html("<span style='color:red;'><spring:message code='public.fileTypeError'/></span>");
				return false;
			}
		},

		// 上传完成后取得文件名filename为本地取得的文件名，msg为服务器返回的信息
		onComplete : function(filename, msg) {
			
			$("#progress-bar").html(100 + "%");
			$("#progress-bar").attr("aria-valuenow",100);
			$("#progress-bar").css("width", 100 + "%");
			intervalId=window.clearInterval(intervalId)
			// 上传可用
			$('#uploadButton').show();
			
			if(msg.success == 'true'){
				//放到form
				$("#filesDIV").append("<input type='hidden' name='attachments' id='attachment"+msg.fileId+"' value='"+msg.fileId+"'>")
				$("#uploading").empty();
				var count =$("#filesLabel label").length+1;
				$("#filesLabel").append("<label>&nbsp;&nbsp;&nbsp;&nbsp;"+count+".&nbsp;"+filename+"<a href='javascript:void(0)' onclick='delAttach("+msg.fileId+",this)'>&nbsp;&nbsp;<i class='fa fa-minus-circle'></i></a></label><br/>");
				
				var splitNames = filename.split(".");
				var tmpExt = splitNames[splitNames.length-1].toLowerCase();
				if(tmpExt == "pdf"){
					$("#dumpImageDiv").append('<img id="uploadImg'+msg.fileId+'" src="${ctx}/static/images/pdf.png" style="height:120px;width:95px;margin-right:10px;" />');
				}else{
					$("#dumpImageDiv").append('<img id="uploadImg'+msg.fileId+'" src="${ctx}/file/dumpImage?path='+msg.filePath+'" style="height:120px;width:100px;margin-right:10px;" />');
				}
				
				// 同步上传和页面label一致
				syncAttach();
			}else{
				$('#uploading').html("<span style='color:red;'>"+msg.message+"</span>");
			}
			
		
		}
	});
	

	function myInterval() {
		$("#progress").html("");
		$.ajax({
			type : "GET",
			url : "${ctx}/file/progress",
			dataType : "json",
			success : function(data) {
				if (data.success == true) {
					$("#progress-bar").html(data.msg + "%");
					$("#progress-bar").attr("aria-valuenow",data.msg);
					$("#progress-bar").css("width", data.msg + "%");
				}

			}
		});
	}
	
</script>