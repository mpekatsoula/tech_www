<%@page import="java.util.ArrayList"%>
<%@page import="restricted.AdminDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href='http://fonts.googleapis.com/css?family=Crafty+Girls' rel='stylesheet' type='text/css'>
    <title>Restricted Page</title>
    
    
    <link rel="stylesheet" media="all" href="../css/style.css"/>
    <link rel="stylesheet" type="text/css" href="../css/reset2.css">
    <link rel="stylesheet" type="text/css" href="../css/structure.css">
    <link rel="stylesheet" href="../css/flexslider.css" >
    <link rel="stylesheet" media="screen" href="../css/superfish.css" />
    <link rel="stylesheet" media="all" href="../css/lessgrid.css"/>

    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <script src="../js/jquery-1.6.4.min.js"></script>

    <!--  <script src="js/less-grid-4.js"></script> -->
    <script src="../js/custom.js"></script>
    <script src="../js/tabs.js"></script>

    <!-- Masonry -->
    <script src="../js/masonry.min.js" ></script>
    <script src="../js/imagesloaded.js" ></script>
    <!-- ENDS Masonry -->

    <!-- Tweet -->
    <link rel="stylesheet" href="../css/jquery.tweet.css" media="all"  /> 
    <script src="../js/tweet/jquery.tweet.js" ></script> 
    <!-- ENDS Tweet -->

    <!-- superfish -->
 
    <script  src="../js/superfish-1.4.8/js/hoverIntent.js"></script>
    <script  src="../js/superfish-1.4.8/js/superfish.js"></script>
    <script  src="../js/superfish-1.4.8/js/supersubs.js"></script>
    <!-- ENDS superfish -->

    <!-- prettyPhoto -->
    <script  src="../js/prettyPhoto/js/jquery.prettyPhoto.js"></script>
    <link rel="stylesheet" href="../js/prettyPhoto/css/prettyPhoto.css"  media="screen" />
    <!-- ENDS prettyPhoto -->

    <!-- poshytip -->
    <link rel="stylesheet" href="../js/poshytip-1.1/src/tip-twitter/tip-twitter.css"  />
    <link rel="stylesheet" href="../js/poshytip-1.1/src/tip-yellowsimple/tip-yellowsimple.css"  />
    <script  src="../js/poshytip-1.1/src/jquery.poshytip.min.js"></script>
    <!-- ENDS poshytip -->


    <!-- GOOGLE FONTS -->
    <link href='http://fonts.googleapis.com/css?family=Allan:700' rel='stylesheet' type='text/css'>

    <!-- Flex Slider -->
    <link rel="stylesheet" href="../css/flexslider.css" >
    <script src="../js/jquery.flexslider-min.js"></script>
    <!-- ENDS Flex Slider -->

    <!--[if IE 6]>
    <link rel="stylesheet" href="css/ie6-hacks.css" media="screen" />
    <script type="text/javascript" src="js/DD_belatedPNG.js"></script>
    <script>
    /* EXAMPLE */
    DD_belatedPNG.fix('*');
    </script>
    <![endif]-->

    <!-- Lessgrid -->
    <link rel="stylesheet" media="all" href="../css/lessgrid.css"/>

    <!-- modernizr -->
    <script src="../js/modernizr.js"></script>
  </head>
  <body>
      
    <header>
			
				
    <div class="wrapper">

    <a href="../index.html" id="logo"><img  src="../img/logo.png" alt="Tandem"></a>

    <nav>
      <ul id="nav" class="sf-menu">
        <li class="current-menu-item"><a href="adminpage.jsp">welcome admin</a></li>	
      </ul>
    </nav>
			
<div class="clearfix"></div>
		
  </div>
</header>
    
    <div id="main">
						
		
<!-- Content -->
<div id="content">
	
  <!-- slider -->
  <div class="flexslider home-slider">
    <ul class="slides">
    <li>
      
    </li>
    </ul>
  </div>
  <div class="shadow-slider"></div>

  <div class="headline">
    <% if(request.isUserInRole("Admin") || request.isUserInRole("admin")) { %>
              
    <% AdminDB lola = new AdminDB();
       ArrayList sd = new ArrayList();
       sd = lola.DisplayUsers();
       int x = sd.size();
       int z = 0;
       while(z < x){out.print(sd.get(z) + "<br>"); z++;}
    %>
    <form method="post"  action="AdminDB.do" >		
      <fieldset class="boxBody">
        <label>Username</label>
        <input value="" name="username" class="text-input" type="text" />
      </fieldset>
      <footer>
        <p><br>
          <input class="button" style="font-family: Tahoma" type="submit" value="Submit" />
        </p>
      </footer>				
    </form>
    <% } else {%>
      You are <b>not</b> authorized to view this page<br/>
    <% } %>
          
  <br>
    <div class="fancy-fonts">
    
    </div>
  </div>

  <div class="clearfix"></div>

  </div>

</div>

    
  </body>
</html>
