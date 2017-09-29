<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="com.asgab.core.pagination.Page"
	required="true"%>
<%@ attribute name="paginationSize" type="java.lang.Integer"
	required="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	int current = page.getPageNumber();
	int begin = Math.max(1, current - paginationSize / 2);
	int end = Math.min(begin + (paginationSize - 1), page.getPageCount());
	request.setAttribute("current", current);
	request.setAttribute("begin", begin);
	request.setAttribute("end", end);
%>

		<ul class="pagination pull-left" style="margin-top: 10px;margin-bottom: 0px;">
			<%
			String lang = request.getLocale().getLanguage();
			if("zh".equals(lang)){
				%>
				<li>当前第${current}页，共<%=page.getPageCount()%>页，<%=page.getTotal()%>条记录</li>
				<%
			}else{
				%>
				<li>Page ${current} of <%=page.getPageCount()%>, Total <%=page.getTotal()%> entries</li>
				<%
			}
			%>
		</ul>

		<ul class="pagination pull-right" style="margin-top: 10px;margin-bottom: 0px;">
			<%
				if (page.hasPrevious()) {
			%>
			<li><a href="?pageNumber=1&sort=<%=page.getSort()%>&${search}">&lt;&lt;</a></li>
			<li><a href="?pageNumber=${current-1}&sort=<%=page.getSort()%>&${search}">&lt;</a></li>
			<%
				} else {
			%>
			<li class="disabled"><a href="#">&lt;&lt;</a></li>
			<li class="disabled"><a href="#">&lt;</a></li>
			<%
				}
			%>

			<c:forEach var="i" begin="${begin}" end="${end}">
				<c:choose>
					<c:when test="${i == current}">
						<li class="active"><a
							href="?pageNumber=${i}&sort=<%=page.getSort()%>&${search}">${i}</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="?pageNumber=${i}&sort=<%=page.getSort()%>&${search}">${i}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<%
				if (page.hasNext()) {
			%>
			<li><a href="?pageNumber=${current+1}&sort=<%=page.getSort()%>&${search}">&gt;</a></li>
			<li><a
				href="?pageNumber=${page.pageCount}&sort=<%=page.getSort()%>&${search}">&gt;&gt;</a></li>
			<%
				} else {
			%>
			<li class="disabled"><a href="#">&gt;</a></li>
			<li class="disabled"><a href="#">&gt;&gt;</a></li>
			<%
				}
			%>

		</ul>


