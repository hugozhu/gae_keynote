<%@ page import="com.jute.google.perf.model.Page" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Properties" %>
<h1>Site Monitor</h1>
<table border="1">
    <tr>
        <th>Page</th>
        <th>Code</th>
        <th>Latency</th>
        <th>Time</th>
        <th>Bytes</th>
        <th>Alert</th>
        <th>Delete ?</th>
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
            String diffString = "";
            if (diff > 60) {
                diffString = (int) (diff/60) + " minutes";
            }
            else {
                diffString = diff + " seconds";
            }
            String status = p.getStatus();
            String action = null;
            if (status==null || status.length()==0) {
                status = "Set Alert";
                action = "update_page.admin?id="+p.getId()+"&status=alert";
            }
            else {
                status = "Unset Alert";
                action = "update_page.admin?id="+p.getId()+"&status=";
            }
    %>
    <tr>
        <td><a href="top_data_points?id=<%=p.getId()%>" target="_blank"><%=p.getUrl()%></a></td>
        <td><%=props.getProperty("code","N/A")%></td>
        <td><%=props.getProperty("last_total")%> ms</td>
        <td><%=diffString%> ago</td>
        <td><%=props.getProperty("length","N/A")%></td>
        <td><a href="<%=action%>"><%=status%></a></td>
        <td><a href="delete_page.admin?url=<%=p.getUrl()%>" onclick="return confirm('Are you sure?')">Delete</a></td>
    </tr>
    <%
        }
    %>
</table>