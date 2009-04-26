<%@ page import="com.jute.google.perf.model.Page" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jute.google.perf.model.DataPoint" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    Page mypage = (Page) request.getAttribute("page");
    List<DataPoint> points = (List<DataPoint>) request.getAttribute("points");
    TimeZone timezone = TimeZone.getTimeZone("GMT+8");
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    format.setTimeZone(timezone);
%>
<h1><%=mypage.getUrl()%> latest performance data:</h1>
<table border="1">
    <tr>
        <th>Date</th>
        <th>Response Code</th>
        <th>Connect Time (ms)</th>
        <th>Read Time (ms)</th>
        <th>Total Time (ms)</th>
        <th>Content Length (bytes)</th>
    </tr>
    <%
        int n = 0;
        int total = 0;
        for(DataPoint p: points) {
            String color=p.getCode()==200?"#FFFFFF":"#FF0000";
            if (p.getCode()==200) {
                n++;
                total+=p.getTotalTime();
            }
    %>
    <tr bgcolor="<%=color%>">
        <td><%=format.format(p.getDate())%></td>
        <td><%=p.getCode()%></td>
        <td><%=p.getConnectTime()%></td>
        <td><%=p.getReadTime()%></td>
        <td><%=p.getTotalTime()%></td>
        <td><%=p.getLength()%></td>

    </tr>
    <%
        }
        int average = (int) (total/(n+0.00000001f));
    %>
    <tr>
        <td colspan="6">Average Total Time: <%=average%> ms</td>
    </tr>
</table>