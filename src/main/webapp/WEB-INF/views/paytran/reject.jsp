<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!-- Modal -->
<div class="modal fade" id="rejectModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<form id="rejectForm" action="${ctx}/paytran/reject" method="post">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">
					<%
          			if("zh".equalsIgnoreCase(request.getLocale().getLanguage())){
          				out.print("请输入拒绝理由:");
          			}else{
          				out.print("Please enter a reason for payment rejection:");
          			}
          			%>
				</h4>
			</div>
			<div class="modal-body">
				<input type="hidden" name="tranNum" value="${payTranHeader.tranNum}"/>
				<textarea class="form-control" rows="5" name="description"></textarea>
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn btn-primary" ><spring:message code='public.commit'/></button>
			</div>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
	
	
</script>