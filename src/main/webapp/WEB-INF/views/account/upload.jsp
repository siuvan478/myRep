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
				<button type="button" class="close" data-dismiss="modal"	aria-label="Close">
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
					<span id='uploading'></span>
					<a href="#" class="thumbnail">
					<img id="uploadImg${attach.attachmentId}" src="${ctx}/file/dumpImage?path=/usr/tmp/upload/1c59e2307bfaa702fe79378dcbea977c_t016b6eddf7901eb9fd.jpg"  />
						</a>
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
	var url = '<%=request.getParameter("upload_url")%>';
	var uploadObject = new AjaxUpload('uploadButton', {
		// 文件上传Action
		action : '${ctx}'+url,
		// 名称
		name : 'file',
		// 自动上传
		autoSubmit : true,
		// 返回Text格式数据
		responseType : 'json',
		// 上传处理
		onSubmit : function(filename, ext) {
			//ext是后缀名
			if(ext && /^(jpg|jpeg|png|gif|doc|xlsx|zip)$/.test(ext)){
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
			$("#uploading").empty();
			if(msg.success == "false"){
				$("#uploading").html("<span style='color:red;'>"+msg.message+"</span>");
			}else{
				$("#uploading").html(filename+":"+ msg.message);
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