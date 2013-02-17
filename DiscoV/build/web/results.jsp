<%@page import="java.util.ArrayList"%>
<%@page import="discov.database.LatestSearches"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
    <link rel="stylesheet" media="all" href="css/framecss.css"/>
    <link rel="stylesheet" href="css/nanoscroller.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Results</title>
        <% LatestSearches latest = new LatestSearches();   
        %>
    <script type="text/javascript">
      var arrayToModify = [];
      window.onload = function () {
        var i, Artist,Song, resultsContainer, newResult;
        <%  ArrayList results = new ArrayList();
            results = latest.Latest_Searches(request); 
            String Artist = "";
            String Song = "";
            for (int i = 0; i < results.size()/2; i++) {
              if (i == 0) {
                Artist += "\"" + (results.get(results.size() -2 - i*2)).toString() + "\"";
                Song += "\"" + (results.get(results.size()- (i*2) -1)).toString() + "\"";
              } 
              else {
                Artist += ",\"" + (results.get(results.size()- i*2 -2)).toString() + "\"";
                Song += ",\"" + (results.get(results.size()- (i*2) -1)).toString() + "\"";                
              }
            }
        %>
        Artist = [<%= Artist %>];
        Song = [<%= Song %>];
        resultsContainer = document.getElementById('results_container');
        
        function changeColor(element) {
          
          links = document.getElementsByTagName("label") ;  
          for (var i = 0 ; i < links.length ; i ++) { 
            links.item(i).style.color = '' ;  
            links.item(i).style.background = '';
          }
          element.style.background = '-moz-linear-gradient(top,  #808080, #282828 )' ;
          element.style.background = '-webkit-gradient(linear, left top, left bottom, from(#808080), to(#282828))' ;
          element.style.color = '#fe8300' ;
        }
        
        for (i = 0; i < Artist.length; i++) {
          newResult = document.createElement('label');
          newResult.className = "results_css";
          newResult.innerHTML = Artist[i] + " - " + Song[i];
          newResult.style.height = "60px";
          newResult.style.display = "block";
          newResult.setAttribute("artist", Artist[i]);
          newResult.setAttribute("song", Song[i]);
          newResult.onclick = function go() {
            arrayToModify[arrayToModify.length] = this.id;
            parent.possible_frame.location ="http://m1nato.no-ip.org:8080/DiscoV/posresults.jsp?artist=" + this.getAttribute("artist") + "&song=" + this.getAttribute("song");
            changeColor(this);
          };
          resultsContainer.appendChild(newResult);
        }
      };


    </script>
    
    </head>
    
  <body>

 
      <div id='results_container' class='nano'>
          
      </div>
    <script type="text/javascript" src="js/jquery.nanoscroller.min.js"></script>
    <script type="text/javascript" src="js/main.js"></script>
  </body>
</html>
