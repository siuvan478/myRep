<%@ page contentType="text/html;charset=UTF-8"%>

<div class="modal fade" id="delcfmModel">
    <div class="modal-dialog">
        <div class="modal-content message_align">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title"><spring:message code="delete.modal.title" /></h4>
            </div>
            <div class="modal-body">
                <p><spring:message code="delete.modal.body" /></p>
            </div>
            <div class="modal-footer">
                <input type="hidden" id="url"/>
                <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="public.cancel" /></button>
                <a onclick="urlSubmit()" class="btn btn-success" data-dismiss="modal"><spring:message code="public.confirm" /></a>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->