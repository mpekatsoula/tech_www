<%-- 
    Document   : index
    Created on : Dec 27, 2012, 7:08:15 PM
    Author     : AD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" media="all" href="css/style.css"/>
    <link rel="stylesheet" type="text/css" href="css/reset2.css">
    <link rel="stylesheet" type="text/css" href="css/structure.css">
    <link rel="stylesheet" href="css/flexslider.css" >
    <link rel="stylesheet" media="screen" href="css/superfish.css" /> 
    <link rel="stylesheet" media="all" href="css/lessgrid.css"/>
    <title>Skratch: Login</title>
  </head>
   
  <body>
    
    <header>
      <div class="wrapper">
        <a href="index.html" id="logo"><img  src="img/logo.png" alt="Tandem"></a>
        <nav>
          <ul id="nav" class="sf-menu">
          <li><a href="index.html">home<span class="subheader">go back</span></a></li>
          <li><a href="register.html">register<span class="subheader">join now</span></a></li>
          </ul>
        </nav>
			
        <div class="clearfix"></div>

      </div>
    </header>
    
    <div id="content">
      <div style="margin-top: 22%">
        <%  String error = request.getParameter("Error");
            if (error != null) {
              if ( error.equals("NoLogin") )
        %><p style="text-align: center;color: #860404;font-size: 14px">
          <%
                out.println("Error! You have to login to continue!"); 
          %></p><br><%
            }
        %>
      </div>
    <div id="login-content">
    <form method="post" class="box login" action="Login.do" >		
      <fieldset class="boxBody">
        <label>Username</label>
        <input value="" name="username" class="text-input" type="text" />
        <label>Password</label>
        <input name="password" class="text-input" type="password" />
      </fieldset>
      <footer>
        <label><input type="checkbox" tabindex="3" name="remember_me" value="on">Keep me logged in</label>
        <p><br>
          <input class="button" style="font-family: Tahoma" type="submit" value="Sign In" />
          <input class="button" style="font-family: Tahoma" type="button" value="Register" onclick="location.href='register.html'" />
        </p>
      </footer>				
    </form>
    </div>    
    </div>
        
  </body>
</html>
