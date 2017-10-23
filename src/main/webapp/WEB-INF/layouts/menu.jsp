<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<header class="main-header">

    <nav class="navbar navbar-static-top ">
        <div class="container">
            <div class="navbar-header">
                <a href="${ctx}/user" class="navbar-brand"><img style="width: 40px; height: 40px; margin-top: -6px;" src="${ctx}/static111/images/help_logo.png"></a>
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse">
                    <i class="fa fa-bars"></i>
                </button>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse pull-left" id="navbar-collapse">
                <ul class="nav navbar-nav">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">会员管理 <span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="${ctx}/user">会员列表</a></li>
                            <li><a href="${ctx}/address">取货地址列表</a></li>
                        </ul>
                    </li>

                    <li><a href="${ctx}/product">产品管理</a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">订单管理 <span class="badge">2</span><span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="${ctx}/order">订单列表</a></li>
                            <li><a href="${ctx}/boxService">服务列表</a></li>
                        </ul>
                    </li>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">设置 <span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="${ctx}/area">区域</a></li>
                            <li><a href="${ctx}/config">配置</a></li>
                        </ul>
                    </li>
                </ul>
            </div><!-- /.navbar-collapse -->
            <!-- Navbar Right Menu -->
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="javascript:void(0)" class="dropdown-toggle" style="cursor: default;height: 50px;">
                            <span class="x-lang-ch <c:if test="${pageContext.response.locale.language=='zh' }">selected</c:if>"
                                  onclick="changeLang('zh_CN')"></span>
                            <span class="x-lang-en <c:if test="${pageContext.response.locale.language=='en' }">selected</c:if>"
                                  onclick="changeLang('en_US')"></span>
                        </a>
                    </li>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                            <span class="glyphicon glyphicon-user"></span>
                            <span class="hidden-sm hidden-xs"><shiro:principal/></span>
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="${ctx}/profile"><i class="fa fa-user fa-fw"></i><spring:message code="header.userprofile" /></a></li>
                            <li class="divider"></li>
                            <li><a href="${ctx}/logout"><i class="fa fa-sign-out fa-fw"></i></i><spring:message code="header.logout" /></a></li>
                        </ul>
                    </li>

                </ul>
            </div><!-- /.navbar-custom-menu -->
        </div><!-- /.container-fluid -->
    </nav>
</header>