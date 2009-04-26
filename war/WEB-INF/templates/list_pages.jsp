<%@ page import="com.jute.google.perf.model.Page" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Properties" %>
<h1>Site Monitor</h1>
<table border="1">
    <tr>
        <th>Page</th>
        <th>Latency</th>
        <th>Time</th>
    </tr>
    <%
        List<Page> pages = (List<Page>) request.getAttribute("pages");
        int now = (int) (System.currentTimeMillis()/1000);
        for(Page p: pages) {
            Properties props = p.getProperties();
            int before = now;
            if (props.contains("last_modified")) {
                before = Integer.parseInt((String)props.get("last_modified"));
            }
            int diff = now - before;
    %>
    <tr>
        <td><a href="top_data_points?id=<%=p.getId()%>"><%=p.getUrl()%></a></td>
        <td><%=props.get("last_total")%> ms</td>
        <td><%=diff%> seconds ago</td>
    </tr>
    <%
        }
    %>
</table>