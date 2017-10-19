<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div class="modal-dialog">
    <div class="modal-content message_align">
        <form action="${ctx}/product/scale/update" method="post" class="form-horizontal">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
            <h4 class="modal-title">
                <c:if test="${action2 eq 'create' }">新建产品规格</c:if>
                <c:if test="${action2 eq 'update' }">编辑产品规格</c:if>
            </h4>
        </div>
        <div class="modal-body">
                <input type="hidden" name="id" value="${scaleForm.id}">
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="col-md-3 control-label">规格:</label>
                        <div class="col-md-9">
                            <input class="form-control" type="text" id="productName" name="productName" value="${scaleForm.scale}">
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="col-md-3 control-label" for="twelveMonthPrice">购买1年单价:</label>
                        <div class="input-group col-md-9" style="padding: 0px 15px 0px 15px;">
                            <div class="input-group-addon">$</div>
                            <input type="text" class="form-control" id="twelveMonthPrice" placeholder="Amount" value="${scaleForm.twelveMonthPrice}">
                            <div class="input-group-addon">HKD</div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="col-md-3 control-label" for="sixMonthPrice">购买6个月单价:</label>
                        <div class="input-group col-md-9" style="padding: 0px 15px 0px 15px;">
                            <div class="input-group-addon">$</div>
                            <input type="text" class="form-control" id="sixMonthPrice" placeholder="Amount" value="${scaleForm.sixMonthPrice}">
                            <div class="input-group-addon">HKD</div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="col-md-3 control-label" for="threeMonthPrice">购买3个月单价:</label>
                        <div class="input-group col-md-9" style="padding: 0px 15px 0px 15px;">
                            <div class="input-group-addon">$</div>
                            <input type="text" class="form-control" id="threeMonthPrice" placeholder="Amount" value="${scaleForm.threeMonthPrice}">
                            <div class="input-group-addon">HKD</div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="col-md-3 control-label" for="oneMonthPrice">购买1个月单价:</label>
                        <div class="input-group col-md-9" style="padding: 0px 15px 0px 15px;">
                            <div class="input-group-addon">$</div>
                            <input type="text" class="form-control" id="oneMonthPrice" placeholder="Amount" value="${scaleForm.oneMonthPrice}">
                            <div class="input-group-addon">HKD</div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="col-md-3 control-label">库存:</label>
                        <div class="col-md-9">
                            <input class="form-control" type="text" id="num" name="num" value="${scaleForm.num}">
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