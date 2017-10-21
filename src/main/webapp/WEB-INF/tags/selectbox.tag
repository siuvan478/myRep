<%@tag import="org.apache.commons.lang3.StringUtils"%>
<%@tag import="java.util.Map"%>
<%@tag import="java.util.Iterator"%>
<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="name" type="java.lang.String" required="true"%>
<%@ attribute name="value" type="java.lang.String" %>
<%@ attribute name="map" type="java.util.Map" required="true"%>
<%@ attribute name="empty" type="java.lang.Boolean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<select name="<%=name%>" class="form-control <%=name%>" id="<%=name%>">
	<%
		if(empty != null && empty){
	%>
			<option value></option>
	<%
		}
	%>
	<%
		Iterator iterator = map.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<Integer,String> entry = (Map.Entry<Integer,String>)iterator.next();
			String selected = "";
			if(StringUtils.isNoneBlank(value) && value.equals(String.valueOf(entry.getKey()))){
				selected="selected=\"selected\"";
			}
	%>
			<option value="<%=entry.getKey() %>" <%=selected %> ><%=entry.getValue() %></option>
	<%
		}
	%>
</select>