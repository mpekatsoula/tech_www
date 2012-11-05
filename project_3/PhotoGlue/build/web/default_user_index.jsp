
<!DOCTYPE html>
<html>
    <head>
        <title>Welcome to PhotoGlue</title>
        <link href="../../default_style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="../../buttons/buttons.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        
        <div id="border1">
            <form action="../../upload.do" method="POST" enctype="multipart/form-data">
            <p>Select your image to upload</p> <br>
            <p><input type="file" name="file1" /></p>
            <input class="button small blue" type="submit" />
            </form>
        </div>
        
        <%@page import="myXML.XMLapps"%>
  
        <div id ="border2">
            <%
                XMLapps filescan = new XMLapps();
                /* Get the tags of the photos */
                String[] Image_Tags = filescan.ScanXMLFiles("/usr/local/tomcat/webapps" + request.getRequestURI().substring(0, request.getRequestURI().length() -10));
                /* Display them! */
                for ( int i = 0; i < Image_Tags.length; i++ ){
                    out.println(Image_Tags[i]);
                }
            %>
           
            
        </div>
        

        
        <div id="footer">
            <div class="tri"></div>
            <h1>Your personal album!</h1>
        </div>

        
    </body>
</html>
