<%@ page import="com.jute.google.perf.model.Page" %>
<%@ page import="com.jute.google.perf.model.DataPoint" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.googlecode.charts4j.*" %>
<%@ page import="java.util.*" %>

<%
    Page mypage = (Page) request.getAttribute("page");
    List<DataPoint> points = (List<DataPoint>) request.getAttribute("points");
    Collections.sort(points, new Comparator<DataPoint>() {
        public int compare(DataPoint o1, DataPoint o2) {
            return o2.getDate().compareTo(o1.getDate());
        }
    });
    TimeZone timezone = TimeZone.getTimeZone("GMT+8");
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    format.setTimeZone(timezone);

    DateFormat format2 = new SimpleDateFormat("HH:mm:ss");
    format2.setTimeZone(timezone);

    StringBuilder chartUrl = new StringBuilder();

    // Defining lines
    final int NUM_POINTS = 200;
    final double[] data = new double[NUM_POINTS];

    Date maxDate = null;
    for (int i = NUM_POINTS-1; i >=0; i--) {
        if (points.size()- (NUM_POINTS - i) >=0) {
            DataPoint p = points.get(NUM_POINTS - i-1);
            if (p.getTotalTime()==0 || p.getTotalTime()>3000) {
                data[i] = 100;
            }
            else {
                data[i] = p.getTotalTime()/30;
            }
            if (maxDate == null) {
                maxDate = (Date) p.getDate().clone();
            }
        }
        else {
            data[i] = -1;
        }

    }

    Line line1 = Plots.newLine(Data.newData(data), Color.newColor("CA3D05"), "Latency");
    line1.setLineStyle(LineStyle.newLineStyle(3, 1, 0));
//    line1.addShapeMarkers(Shape.CIRCLE, Color.newColor("CA3D05"), 12);
//    line1.addShapeMarkers(Shape.CIRCLE, Color.WHITE, 8);

    LineChart chart = GCharts.newLineChart(line1);
    chart.setSize(600,450);
    chart.setTitle(mypage.getUrl().substring(7)+" latency in ms", Color.WHITE, 14);
    chart.setGrid(10, 10, 1, 1);

    // Defining axis info and styles
    String[] dates = new String[10];
    for(int i=0;i<dates.length;i++) {
        dates[dates.length-i-1] = format2.format(maxDate);
        maxDate.setTime(maxDate.getTime() - 20*60*1000l);
    }

    AxisStyle axisStyle = AxisStyle.newAxisStyle(Color.WHITE, 12, AxisTextAlignment.CENTER);
    AxisLabels xAxis = AxisLabelsFactory.newAxisLabels(dates);
    xAxis.setAxisStyle(axisStyle);
    chart.addXAxisLabels(xAxis);

    AxisLabels yAxis = AxisLabelsFactory.newAxisLabels("0", "500", "1000" , "1500", "2000", "2500","3000");
    chart.addYAxisLabels(yAxis);    

    // Defining background and chart fills.
    chart.setBackgroundFill(Fills.newSolidFill(Color.newColor("1F1D1D")));
    LinearGradientFill fill = Fills.newLinearGradientFill(0, Color.newColor("363433"), 100);
    fill.addColorAndOffset(Color.newColor("2E2B2A"), 0);
    chart.setAreaFill(fill);

    chartUrl.append(chart.toURLString());   
%>
<h1><a href="<%=mypage.getUrl()%>" target="_blank"><%=mypage.getUrl()%></a> latest performance data:</h1>
<img src="<%=chartUrl%>"/>
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
        Date max= null, min = null;
        for(DataPoint p: points) {
            String color=p.getCode()==200?"#FFFFFF":"#FF0000";
            if (p.getCode()==200) {
                n++;
                total+=p.getTotalTime();
            }
            if (max == null) {
                max = p.getDate();
                min = p.getDate();
            }
            if (p.getDate().getTime() < min.getTime()) {
                min = p.getDate();
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
        int minutes = (int) ( (max.getTime() - min.getTime())/(1000*60l));        
    %>
    <tr>
        <td colspan="6">Average Total Time: <%=average%> ms within <%=minutes%> minutes</td>
    </tr>
</table>