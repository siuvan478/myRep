<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div class="modal-dialog">
    <div class="modal-content message_align">
        <form id="scaleForm" action="${ctx}/product/scale/${action2}" method="post" class="form-horizontal">
        <input type="hidden" name="id" value="${scaleForm.id}" />
        <input type="hidden" name="productId" value="${scaleForm.productId}" />
        <input type="hidden" name="needQuote" value="${scaleForm.needQuote}" />
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
            <h4 class="modal-title">
                <c:if test="${action2 eq 'create' }"><spring:message code="scale.form.create.title" /></c:if>
                <c:if test="${action2 eq 'update' }"><spring:message code="scale.form.edit.title" /></c:if>
            </h4>
        </div>
        <div class="modal-body">
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="col-md-3 control-label"><spring:message code="scale.form.name" />:</label>
                        <div class="col-md-9">
                            <input class="form-control" type="text" id="scale" name="scale" value="${scaleForm.scale}">
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="col-md-3 control-label" for="twelveMonthPrice"><spring:message code="scale.form.twelveMonthPrice" />:</label>
                        <div class="input-group col-md-9" style="padding: 0px 15px 0px 15px;">
                            <div class="input-group-addon">$</div>
                            <input type="text" class="form-control" name="twelveMonthPrice" id="twelveMonthPrice"
                                   placeholder="Amount" value="${scaleForm.twelveMonthPrice}">
                            <div class="input-group-addon">HKD</div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="col-md-3 control-label" for="sixMonthPrice"><spring:message code="scale.form.sixMonthPrice" />:</label>
                        <div class="input-group col-md-9" style="padding: 0px 15px 0px 15px;">
                            <div class="input-group-addon">$</div>
                            <input type="text" class="form-control" name="sixMonthPrice" id="sixMonthPrice"
                                   placeholder="Amount" value="${scaleForm.sixMonthPrice}">
                            <div class="input-group-addon">HKD</div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="col-md-3 control-label" for="threeMonthPrice"><spring:message code="scale.form.threeMonthPrice" />:</label>
                        <div class="input-group col-md-9" style="padding: 0px 15px 0px 15px;">
                            <div class="input-group-addon">$</div>
                            <input type="text" class="form-control" name="threeMonthPrice" id="threeMonthPrice"
                                   placeholder="Amount" value="${scaleForm.threeMonthPrice}">
                            <div class="input-group-addon">HKD</div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="col-md-3 control-label" for="oneMonthPrice"><spring:message code="scale.form.oneMonthPrice" />:</label>
                        <div class="input-group col-md-9" style="padding: 0px 15px 0px 15px;">
                            <div class="input-group-addon">$</div>
                            <input type="text" class="form-control" name="oneMonthPrice" id="oneMonthPrice"
                                   placeholder="Amount" value="${scaleForm.oneMonthPrice}">
                            <div class="input-group-addon">HKD</div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="col-md-3 control-label"><spring:message code="scale.form.number" />:</label>
                        <div class="col-md-9">
                            <input class="form-control" type="text" id="num" name="num" value="${scaleForm.num}">
                        </div>
                    </div>
                </div>
            <div class="clearfix"></div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="public.cancel" /></button>
            <button type="submit" class="btn btn-success"><spring:message code="public.commit" /></button>
        </div>
        </form>
    </div>
</div>

<script type="text/javascript">
    $("#scaleForm").validate({
        rules : {
            scale : "required",
            twelveMonthPrice : {
                required:false,
                number:true,
                min:0
            },
            sixMonthPrice : {
                required:false,
                number:true,
                min:0
            },
            threeMonthPrice : {
                required:false,
                number:true,
                min:0
            },
            oneMonthPrice : {
                required:false,
                number:true,
                min:0
            },
            num : {
                required:false,
                number:true,
                min:0
            },
            description : "required"
        },
        messages: {

        }
    });
</script>