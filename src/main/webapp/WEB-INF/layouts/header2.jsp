<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="com.asgab.service.account.ShiroDbRealm.ShiroUser"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
 <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <!-- 
                <a class="navbar-brand" href="#">
                	<img alt="Brand" height="30px;" width="100px;" src="${ctx}/static/images/iclick-logo.png">
                </a>
                 -->
                 <a class="navbar-brand" style="color:black;" href="javascript:void(0)">
                	这里显示LOGO
                </a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
            	  <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <spring:message code="index.welcome" />, <shiro:principal property="name"/>
                    </a>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                    	<%
                    	ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
                    	%>
                        <li><a href="${ctx}/profile"><i class="fa fa-user fa-fw"></i><spring:message code="header.userprofile" /></a></li>
                        <li class="divider"></li>
                        <li><a href="${ctx}/logout"><i class="fa fa-sign-out fa-fw"></i></i><spring:message code="header.logout" /></a></li>
                    </ul>
                </li>
            </ul>

             <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li>
                            <a href="${ctx}/user"><i class="fa fa-users fa-fw"></i><spring:message code="menu.user" /></a>
                        </li>
                        <li>
                            <a href="${ctx}/area"><i class="fa fa-wrench fa-fw"></i>设置<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="${ctx}/area"><spring:message code="menu.area" /></a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>