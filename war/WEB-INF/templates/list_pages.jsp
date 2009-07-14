<%@ page import="com.jute.google.perf.model.Page" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Properties" %>
<h1>Site Monitor</h1>
<table border="1">
    <tr>
        <th>Page</th>
        <th>Latency</th>
        <th>Time</th>
        <th>Alert</th>
    </tr>
    <%
        List<Page> pages = (List<Page>) request.getAttribute("pages");
        int now = (int) (System.currentTimeMillis()/1000l);
        for(Page p: pages) {
            Properties props = p.getProperties();
            int before = now;
            if (props.containsKey("last_modified")) {
                before = Integer.parseInt((String)props.get("last_modified"));
            }
            int diff = now - before;
            String status = p.getStatus();
            String action = null;
            if (status==null || status.length()==0) {
                status = "Set Alert";
                action = "update_page?id="+p.getId()+"&status=alert";
            }
            else {
                status = "Unset Alert";
                action = "update_page?id="+p.getId()+"&status=";
            }

    %>
    <tr>
        <td><a href="top_data_points?id=<%=p.getId()%>"><%=p.getUrl()%></a></td>
        <td><%=props.get("last_total")%> ms</td>
        <td><%=diff%> seconds ago</td>
        <td><a href="<%=action%>"><%=status%></a></td>
    </tr>
    <%
        }
    %>
</table>