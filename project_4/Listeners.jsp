<%-- 
    Document   : Listeners
    Created on : 22 Νοε 2012, 2:44:37 μμ
    Author     : Spyrou Michalis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="listeners.Sessions_lst"%>
<%@page import="listeners.Requests_lst"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="refresh" content="60" >
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script language="javascript" type="text/javascript" src="jquery.js"></script>
        <script language="javascript" type="text/javascript" src="jquery.flot.js"></script>
        <title>Listeners View</title>
    </head>
    <body>
 
        
        Active Sessions:
        <%  
        
        Sessions_lst session_tk = new Sessions_lst();
        out.println(session_tk.getActiveSessions() + "<br>");
        
        /* Now it's the time to write the data to file */
        session_tk.WriteSessionsData("/usr/local/tomcat/webapps/PhotoGlue/data1.json");
        
        %>
        
        Total Image Edits:
        <% 
            Requests_lst requests_tk = new Requests_lst();
            out.println(requests_tk.getEditCount()+"<br>");
            
            /* Write data to file */
            requests_tk.WriteRequestsData();
        %>
        
        Total Image Uploads:
        <% 
            out.println(requests_tk.getUploadCount() + "<br>");
        %>
        
        Total Web App Sessions:
        <% 
            out.println(session_tk.getTotalSessions() + "<br><br>");
        %>
        
        Active Sessions in the last 24h: <br>
        <div id="placeholder1" style="width:600px;height:300px;"></div>
        
        Total Image Edits in the last 24h: <br>
        <div id="placeholder2" style="width:600px;height:300px;"></div>     
        
        Total Image Uploads in the last 24h: <br>
        <div id="placeholder3" style="width:600px;height:300px;"></div>  
        
        
<script type="text/javascript">
$(function () {
    var options = {
        lines: { show: true, fill: true, fillColor: "rgba(250, 0, 0, 0.5)" },
        points: { show: true },
        xaxis: { tickDecimals: 0, tickSize: 1 }
    };
    var data = [];
    var data2 = [];
    var data3 = [];

    alreadyFetched = {};

    var iteration = 0;
        
    function fetchData() {
        ++iteration;

        function onDataReceived1(series) {
            
                data = [ series ];
                $.plot($("#placeholder1"), data, options);
                                
        }
        function onDataReceived2(series) {
            
                data2 = [ series ];
                $.plot($("#placeholder2"), data2, options);
                                
        }
        function onDataReceived3(series) {
            
                data3 = [ series ];                
                $.plot($("#placeholder3"), data3, options);
                                
        }
                $.ajax({
                url: "data2.json",
                method: 'GET',
                dataType: 'json',
                success: onDataReceived2
            });
            
            $.ajax({
                url: "data1.json",
                method: 'GET',
                dataType: 'json',
                success: onDataReceived1
            });
            
            $.ajax({
                url: "data3.json",
                method: 'GET',
                dataType: 'json',
                success: onDataReceived3
            });

     }
     
     
     setTimeout(fetchData, 100);

});
</script>
      
        
    </body>
</html>
