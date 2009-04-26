<%@ page import="com.jute.google.perf.model.Page" %>
<%@ page import="java.util.List" %>
<table border="1">
    <tr>
        <th>Page</th>
        <th>Status</th>
    </tr>
    <%
        List<Page> pages = (List<Page>) request.getAttribute("pages");
        for(Page p: pages) {
    %>
    <tr>
        <td><a href="top_data_points?id=<%=p.getId()%>"><%=p.getUrl()%></a></td>
        <td></td>
    </tr>
    <%
        }
    %>
</table>