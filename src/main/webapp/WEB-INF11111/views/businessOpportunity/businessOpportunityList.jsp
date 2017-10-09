<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@page import="org.springframework.web.servlet.LocaleResolver"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="com.asgab.service.account.ShiroDbRealm.ShiroUser"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="pms" uri="http://i-click/authTag"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
	LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver (request);
	request.setAttribute("lang",localeResolver.resolveLocale(request).getLanguage());
	request.setAttribute("country", localeResolver.resolveLocale(request).getCountry());
%>

<html>
<head>
<title><spring:message code="menu.business.opportunity"/></title>
</head>
<body>
	<style type="text/css">
		.btn-sm{line-height: 1.1;}
		.btn-50{width: 50px;}
		.dateclear {
		    position: absolute;
		    right: 7px;
		    top: 16px;
		    margin: auto;
		    line-height:0px;
		    cursor: pointer;
		    font-weight: bold;
		    z-index: 3;
		}
	</style>
	 <!-- Content Header -->
       <section class="content-header">
          <h1>
            <spring:message code="menu.business.opportunity"/>
          </h1>
          
          <ol class="breadcrumb">
            <li><a href="${ctx}/businessOpportunity"><i class="fa fa-dashboard"></i> <spring:message code="opportunity.home" /></a></li>
            <li class="active"><spring:message code="opportunity.header"/></li>
          </ol>
		
		<c:if test="${message != null}">
			<div class="alert alert-success alert-dismissable" style="margin: 10px 0 0 0">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <h4>	<i class="icon fa fa-check"></i> <spring:message code="message.alert.success"/>!</h4>
                    <c:if test="${successId != null }">
                   	 &lt;${successId}&gt;
                    </c:if>
                    ${message}
                    <c:if test="${orderMessage != null}"><br/>${orderMessage}</c:if>
            </div>
        </c:if>
        
        <c:if test="${message_del != null}">
            <div class="alert ${alert_type} alert-dismissable" style="margin: 10px 0 0 0">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <c:if test="${success == true}">
                         <h4><i class="icon fa fa-check"></i> <spring:message code="message.alert.success"/>!</h4>
                    </c:if>
                    <c:if test="${success != true}">
                         <h4><i class="icon fa fa-close"></i> <spring:message code="message.alert.fail"/>!</h4>
                    </c:if>
                   
                    ${message_del}
                    <c:if test="${orderMessage != null}"><br/>${orderMessage}</c:if>
            </div>
        </c:if>
		          
        </section>

          <!-- Main content -->
          <section class="content">
          	<div class="nav-tabs-custom">
                <div class="tab-content">
                  <div class="tab-pane active" id="activity">
                  <div class="box box-primary">
		            <div class="box-header with-border">
		              <h3 class="box-title">
		             
		              <!-- 没有title -->
		             	<button class="btn btn-primary btn-sm btn-80" onclick="window.location.href='${ctx}/businessOpportunity/create';"><i class="fa fa-w fa-plus"></i>&nbsp;<spring:message code="btn.create"/></button>
                		<div class="btn-group">
                		<button class="btn btn-primary btn-sm btn-80" onclick="$('#searchForm').submit();"><i class="fa fa-w fa-search"></i>&nbsp;<spring:message code="btn.search"/></button>
		              	<button class="btn btn-warning btn-sm btn-80" onclick="resetForm();"><i class="fa fa-w fa-undo"></i>&nbsp;<spring:message code="btn.reset"/></button>
		              	</div>
		              </h3>
		              <div class="box-tools pull-right">
		                <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
		              </div>
		            </div><!-- /.box-header -->
		            <div class="box-body" style="display: block;">
		            	<form action="${ctx}/businessOpportunity" method="get" id="searchForm">
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
									<label><spring:message code="opportunity.id"/></label>
									<input type="text" class="form-control" name="number" id="number" value="<c:out value="${pages.searchMap['number']}"/>" placeholder="<spring:message code='opportunity.id.placeholder'/>">
								</div>
							</div>
							<div class="col-md-4">	
								<div class="form-group">
									<label><spring:message code="business.opportunity.name"/></label>
									<input type="text" class="form-control" name="name" id="name" value="<c:out value="${pages.searchMap['name']}"/>" placeholder="<spring:message code='business.opportunity.input.name'/>">
								</div>
							</div>
							<div class="col-md-4">
							    <div class="form-group">
                                    <label><spring:message code="business.opportunity.advertiser"/></label>
                                    <input type="text" class="form-control" name="advertiser" id="advertiser" value="<c:out value="${pages.searchMap['advertiser']}"/>" placeholder="<spring:message code='business.opportunity.input.advertiser'/>">
                                </div>
							</div>
							
                            <div class="col-md-4">
                                 <div class="form-group">
                                    <label><spring:message code="business.opportunity.created.by"/></label>
                                    <select class="form-control select2 created_by" name="created_by" id="created_by" style="width: 100%;">
                                        <c:forEach var="user" items="${users}">
                                            <option value="${user.id}">${user.name}</option>
                                        </c:forEach>
                                    </select>
                                 </div>
                             </div>
                             <div class="col-md-4">  
                                 <label><spring:message code="business.opportunity.created.date"/></label>  
                                 <div class="form-group input-group">
                                    <lable class="input-group-addon"><i class="fa fa-calendar"></i></lable>
                                    <input type="text" class="form-control" value="${pages.searchMap['created_period']}" 
                                    name="created_period" id="created_period" placeholder="<spring:message code='business.opportunity.created.date.remark' />">
                                    <span class="dateclear">×</span>
                                 </div>
                            </div>
                            
							<div class="col-md-4">
                                <div class="form-group">
                                    <label><spring:message code="business.opportunity.progress"/></label>
                                    <select name="status" class="form-control status" id="status" onchange="$('#searchForm').submit();">
                                        <c:if test="${lang eq 'zh' }">
                                            <option value>全部</option>
                                            <c:forEach var="p" items="${statusesMap}">
                                                <c:if test="${pages.searchMap['status'] == p.value}">
                                                    <option value="${p.value}" selected="selected">${statusesZH[p.value]}&nbsp;${p.key}%</option>
                                                </c:if>
                                                <c:if test="${pages.searchMap['status'] != p.value}">
                                                    <option value="${p.value}">${statusesZH[p.value]}&nbsp;${p.key}%</option>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${lang ne 'zh' }">
                                            <option value>All</option>
                                            <c:forEach var="p" items="${statusesMap}">
                                                <c:if test="${pages.searchMap['status'] == p.value}">
                                                    <option value="${p.value}" selected="selected">${statusesEN[p.value]}&nbsp;${p.key}%</option>
                                                </c:if>
                                                <c:if test="${pages.searchMap['status'] != p.value}">
                                                    <option value="${p.value}">${statusesEN[p.value]}&nbsp;${p.key}%</option>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </select>
                                </div>
                            </div>
						</div><!-- /.row -->
						</form>
					</div><!-- /.box-body -->
					</div>
                	
                	<div class="box-body  table-responsive no-padding">
          
	                  <table class="table table-striped table-condensed table-hover">
	                    <tbody><tr>
	                      <th class="text-right"><spring:message code="opportunity.operate"/></th>
	                      <th <tags:sort column="id" page="${pages}"/> style="width: 110px"><spring:message code="opportunity.id" />&nbsp;<i class="fa fa-w fa-sort"></i></th>
	                      <th <tags:sort column="name" page="${pages}"/>><spring:message code="business.opportunity.name"/>&nbsp;<i class="fa fa-w fa-sort"></i></th>
	                      <th <tags:sort column="advertiser" page="${pages}"/>><spring:message code="business.opportunity.advertiser"/>&nbsp;<i class="fa fa-w fa-sort"></i></th>
	                      <th><spring:message code="business.opportunity.created.by" /></th>
	                      <th><spring:message code="business.opportunity.created.date" /></th>
	                      <th><spring:message code="business.opportunity.status"/></th>
	                      <th style="width: 10%"><spring:message code="opportunity.progress"/></th>
	                      <th><spring:message code="opportunity.label"/></th>
	                    </tr>
                    
	                    <c:forEach items="${pages.content}" var="opportunity" varStatus="status">
	                    	<tr>
	                    	  <td class="text-right">
	                    	      <span class="glyphicon glyphicon-cog" data-toggle="popover" data-id="${opportunity.id}"
	                    	          data-del-msg="<spring:message code='message.confirm.del.opportunity' 
	                    	          arguments="${opportunity.name},${opportunity.number}"
	                    	          argumentSeparator="," />">
	                    	      </span>
	                    	  </td>
		                      <td>${opportunity.number}</td>
		                      <td><a href="javascript:void(0);" onclick="view(${opportunity.id});">${opportunity.name}</a></td>
		                      <td><a href="javascript:void(0);" onclick="window.location.href='${ctx}/client/view/${opportunity.advertiser_id}'">${opportunity.advertiser}</a></td>
		                      <td>${opportunity.username}</td>
		                      <td><fmt:formatDate value="${opportunity.created_at}" pattern="yyyy/MM/dd HH:ss"/></td>
		                      <td>
		                      	<c:choose> 
								  <c:when test="${lang eq 'zh' && country eq 'CN'}">   
								    ${opportunity.getDecodeStatus('zh')}
								  </c:when> 
								  <c:otherwise>   
								    ${opportunity.getDecodeStatus('en')} 
								  </c:otherwise> 
								</c:choose> 
		                      </td>
		                      <td>
		                        <div class="progress progress-xs progress-striped active">
		                          <div class="progress-bar ${opportunity.progressBar.barClass}" style="width: ${opportunity.progressBar.value}%"></div>
		                        </div>
		                      </td>
		                      <td><span class="badge ${opportunity.progressBar.bgClass}">${opportunity.progressBar.value}%</span></td>
		                      <%-- <td style="word-break: keep-all;white-space:nowrap;">
		                      		<pms:auth equal1="${opportunity.created_by}" equal2="${opportunity.owner_sale}" array="${opportunity.cooperate_sales}">
		                      			<a href="javascript:void(0);" onclick="window.location.href='${ctx}/businessOpportunity/update/${opportunity.id}';" 
		                      			class="btn btn-sm btn-warning btn-flat btn-70">
			                      			<i class="fa fa-w fa-edit"></i> <spring:message code="btn.edit"/>
			                      		</a>
			                      		
			                      		<a href="javascript:void(0);" 
			                      		onclick="del(${opportunity.id},'<spring:message code='message.confirm.del.opportunity' arguments='${opportunity.name},${opportunity.number}' argumentSeparator="," />');" 
			                      		class="btn btn-sm btn-danger btn-flat btn-70 <c:if test="${opportunity.status > 2}">disabled</c:if>">
			                      			<i class="fa fa-w fa-trash-o"></i> <spring:message code="btn.delete"/>
			                      		</a>
		                      		</pms:auth>
		                      </td> --%>
		                    </tr>
	                    </c:forEach>
                    
                  </tbody></table>
				</div><!-- /.box-body-->
                <div class="box-footer clearfix">
                  <tags:pagination page="${pages}" paginationSize="5" />
                </div>
			</div></div></div>
          </section>
          
          
          <script type="text/javascript">
          	$(document).ready(function() {
        		$("#menu_business_opportunity").addClass("active");
        		
        		
        		$("#created_by").val("${pages.searchMap['created_by']}").trigger("change");
        		
        		$("#created_by").select2({
        			 placeholder: "<spring:message code='business.opportunity.created.by.remark' />",
                     allowClear: true,
                     language: lang_options(1)
        		}).on('change', function(){
        			$("#searchForm").submit();
        		});
        		
        		$(".dateclear").click(function(){
        			$("#created_period").val('');
        		});
        		
        		$("#created_period").daterangepicker({opens:"right",cancelClass:"btn-info",format:'YYYY/MM/DD',locale :{
                    applyLabel: '<spring:message code="date.apply"/>',
                    cancelLabel: '<spring:message code="date.cancel"/>',
                    fromLabel: '<spring:message code="date.from"/>',
                    toLabel: '<spring:message code="date.to"/>',
                    weekLabel: 'W',
                    customRangeLabel: '<spring:message code="date.customRange"/>',
                    daysOfWeek: moment.weekdaysMin(),
                    monthNames: moment.monthsShort(),
                    firstDay: moment.localeData()._week.dow
                }});
        		
        		// 操作弹出窗
        		$('[data-toggle="popover"]').each(function(){
        			 var element = $(this);
                     var id = element.attr('data-id');
                     var delMsg = element.attr('data-del-msg');
                     element.popover({
                         trigger: 'click',
                         placement: 'right',
                         html: 'true',
                         content: centent(id, delMsg)
                     }).on("mouseenter", function () {
                         var _this = this;
                         $(this).popover("show");
                         $(this).siblings(".popover").on("mouseleave", function () {
                             $(_this).popover('hide');
                         });
                     }).on("mouseleave", function () {
                         var _this = this;
                         setTimeout(function () {
                             if (!$(".popover:hover").length) {
                                 $(_this).popover("hide")
                             }
                         }, 100);
                     });
                });
        	});
          	
          	
          	function centent(id,delMsg){
          		    return '<ul class="list-unstyled text-left">'
       	                   +'<li><a href="javascript:void(0);" onclick="toUpdate(' + id + ')"><spring:message code="btn.edit" /></a></li>'
       	                   +'<li><a href="javascript:void(0);" onclick="del(' + id + ',\''+ delMsg +'\');"><spring:message code="btn.delete" /></a></li>'
       	              +'<ul>';
          	};
          	
          	function toUpdate(id){
          		window.location.href="${ctx}/businessOpportunity/update/"+id;
          	};
          
          	function view(id){
          		window.location.href="${ctx}/businessOpportunity/view/"+id;
          	};

          	function del(id,msg){
          		bootbox.dialog({
          			message: msg,
          			title: "<spring:message code='opportunity.title.delete' />" ,
          		  	buttons: {
          		  		cancel: {
	          		      label: "<spring:message code='btn.cancel' />",
	          		      className: "btn-75 btn-default",
	          		      callback: function() {
	          		    	
	          		      }
	          		    },
	          			success: {
	          		      label: "<spring:message code='btn.delete' />",
	          		      className: "btn-75 btn-danger",
	          		      callback: function() {
	          		    	window.location.href="${ctx}/businessOpportunity/delete/"+id;
	          		      }
	          		    }
          		  	}
          		});
          	};
          	
          	function resetForm(){
          		$("#searchForm input").val('');
          		$("#searchForm select").val(null).change();
          		$('#searchForm').submit();
          	};
          </script>
</body>
</html>
