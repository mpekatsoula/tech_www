<%@page import="de.umass.lastfm.ImageSize"%>
<%@page import="de.umass.lastfm.Track"%>
<%@page import="lastfm.Lastfm_func"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   
    <head>
        <link rel="stylesheet" media="all" href="css/framecss.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lastfm Results</title>    
    </head>
    <body>
        <div id='results_container1'>
          <% 
          Lastfm_func search = new Lastfm_func();
          String image_url= null,art= "null",album= null,wiki= null,time = null,url = null,info=null;

          Track t = search.Track_search(request.getParameter("artist"),request.getParameter("song"));
          if (request.getParameter("song") != null){
            if( request.getParameter("song").equals("No Title")  ){
              info = "<label>No information found :(</label>";
            }
            else{
              image_url = t.getImageURL(ImageSize.MEDIUM);
              art = t.getArtist();
              album = t.getAlbum();
              wiki = t.getWikiSummary();
              
              int t_time = t.getDuration();
              int minutes = t_time/60;
              int seconds = t_time%60;
              time = Integer.toString(minutes) + ":" + Integer.toString(seconds);  
              url = t.getUrl();
            }
          }

         

          if(image_url == null){
            image_url = "";
          }else{
            out.print("<a href=\"" + url + "\" target=\"_blank\"><img src=\"" + image_url +"\" href/></a><br><br>");
          }

          if(art.equals("null") || request.getParameter("artist")==null || request.getParameter("song")==null){
            art = ""; wiki = ""; time=""; url="";
          }else{
            out.print("<label>Artist: </label>" + art + "<br><br>");
            out.print("<label>Duration: </label>" + time + "<br><br>");
          }

          if(wiki == null){
            wiki = "No Summary Found";
          }


          if(album == null){
            album = "";
          }else{
            out.print("<label>Album: </label>" + album + "<br><br>");
            out.print("<label>Wiki Summary: </label>" + wiki);
          }
          
          if (info!=null){
            out.print(info);
          }
        %>

        </div>

    </body>
</html>
