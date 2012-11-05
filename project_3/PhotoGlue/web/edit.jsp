<%@page import="myXML.XMLapps"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit your photos</title>
        <link href="default_style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="buttons/buttons.css" />
    </head>
    <body>
 <script type="text/javascript">         
    function validate(form) {
        var e = form.elements;
        if( e['height'].value > 8000 ) {
            alert('Please enter height between 1 and 8000px');
            return false;
        }
        if( e['height'].value < 0 ) {
            alert('Please enter height between 1 and 8000px');
            return false;
        }
        if( e['width'].value > 8000 ) {
            alert('Please enter height between 1 and 8000px');
            return false;
        }
        if( e['rotate'].value < -360 ) {
            alert('Rotate degrees must be between -360 and 360.');
            return false;
        }
        if( e['rotate'].value > 360 ) {
            alert('Rotate degrees must be between -360 and 360.');
            return false;
        }
        if( e['width'].value < 0 ) {
            alert('Please enter height between 1 and 8000px');
            return false;
        }       

        return true;
    }
</script>
        
        <div id="border2">  
            <%  XMLapps displayimage = new XMLapps();   
            out.println(displayimage.DisplayImage(request.getParameter("loc")));       
            %>
            
        </div>
                
        <div id="border3">
            <% out.println(displayimage.Caption(request.getParameter("loc"))); %>
            <br><br>
            <p>Edit Image Metadata:</p>
            <form action="xmledit.do" method="POST" onsubmit="return validate(this)">
            Image Height <input type="number" name="height"><br>
            Image Width <input type="number" name="width"><br>
            Rotate Degress <input type="number" name="rotate"><br>
            Caption <br><input type="text" name="caption"> <br>
            Grayscale Brightness <input type="number" name="gray_bright"><br>
            <input class="button small blue" type="submit" />
            </form>  
        </div>  
 
        
        
    </body>
</html>
