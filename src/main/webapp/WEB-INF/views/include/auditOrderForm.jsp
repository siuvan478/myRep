<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div class="modal-dialog">
    <div class="modal-content message_align">
        <form action="${ctx}/order/audit" method="post" class="form-horizontal">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
            <h4 class="modal-title">
                订单审核
            </h4>
        </div>
        <div class="modal-body">
            <input type="hidden" name="id" value="${orderForm.id}">
            <div class="col-md-12">
                <div class="form-group">
                    <label class="col-md-3 control-label"></label>
                    <div class="col-md-9">
                        <label class="radio-inline">
                            <input type="radio" name="status" id="status1" value="0"> 取消订单
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="status" checked id="status2" value="2"> 已收款
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">备注:</label>
                    <div class="col-md-9">
                        <textarea class="form-control" id="remark" name="remark" rows="3"></textarea>
                    </div>
                </div>
            </div>
            <div class="clearfix"></div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="submit" class="btn btn-success">确定</button>
        </div>
        </form>
    </div>
</div>