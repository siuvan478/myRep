<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link rel="stylesheet" href="${ctx}/static111/zyupload/skins/zyupload-1.0.0.min.css " type="text/css">
<script type="text/javascript" src="${ctx}/static111/zyupload/zyupload.basic-1.0.0.min.js"></script>
<script type="text/javascript">

    // 初始化插件
    $("#zyupload").zyUpload({
        width            :   "auto",                 // 宽度
        height           :   "auto",                 // 宽度
        itemWidth        :   "50px",                 // 文件项的宽度
        itemHeight       :   "50px",                 // 文件项的高度
        url              :   "${ctx}/upload/image?fileType=9",  // 上传文件的路径
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
            if(result.message != null){
                showTip(result.message);
                return;
            }
            var imageBo = result.imageBo;
            $("#uploadList_"+file.index).attr("flag", "success").attr("url", imageBo.ossUrl);
        },
        onFailure: function(file, response){          // 文件上传失败的回调方法
            console.info("此文件上传失败："+file.name);
            console.info("response:"+response);
        },
        onComplete: function(response){           	  // 上传完成的回调方法
            console.info("文件上传完成");
        }
    });

</script>

<div class="modal-dialog">
    <div class="modal-content message_align">
        <form action="${ctx}/product/scale/${action2}" method="post" class="form-horizontal">
        <input type="hidden" name="id" value="${scaleForm.id}" />
        <input type="hidden" name="productId" value="${scaleForm.productId}" />
        <input type="hidden" name="needQuote" value="${scaleForm.needQuote}" />
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
            <h4 class="modal-title">
                <c:if test="${action2 eq 'create' }">新建产品规格</c:if>
                <c:if test="${action2 eq 'update' }">编辑产品规格</c:if>
            </h4>
        </div>
        <div class="modal-body">
            <div class="col-md-12">
                <div id="zyupload" class="zyupload fl" style="margin-right: 20px;"></div>
                <span style="color: #dd7e6b; margin-top: 100px; display: block;">
							1、最多上传3张<br>
							2、不足3张 则 从新闻内容提取<br>
							3、无图 则 使用默认图
			</span>
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