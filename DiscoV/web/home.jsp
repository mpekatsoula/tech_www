<%@page import="de.umass.lastfm.ImageSize"%>
<%@page import="de.umass.lastfm.Track"%>
<%@page import="cookies.SecurityController"%>
<%@page import="lastfm.Lastfm_func"%>

<!doctype HTML Frameset DTD>
<html class="no-js">
  <head>
    <meta charset="utf-8"/>
    <link href='http://fonts.googleapis.com/css?family=Crafty+Girls' rel='stylesheet' type='text/css'>
    <title>Skratch! Your Results</title>

    <link rel="stylesheet" href="css/nanoscroller.css">
    <!--[if lt IE 9]>
    <script src="js/css3-mediaqueries.js"></script>
    <![endif]-->

    <link rel="stylesheet" media="all" href="css/style.css"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <script src="js/jquery-1.6.4.min.js"></script>

    <!--  <script src="js/less-grid-4.js"></script> -->
    <script src="js/custom.js"></script>
    <script src="js/tabs.js"></script>

    <!-- Masonry -->
    <script src="js/masonry.min.js" ></script>
    <script src="js/imagesloaded.js" ></script>
    <!-- ENDS Masonry -->

    <!-- Tweet -->
    <link rel="stylesheet" href="css/jquery.tweet.css" media="all"  /> 
    <script src="js/tweet/jquery.tweet.js" ></script> 
    <!-- ENDS Tweet -->

    <!-- superfish -->
    <link rel="stylesheet" media="screen" href="css/superfish.css" /> 
    <script  src="js/superfish-1.4.8/js/hoverIntent.js"></script>
    <script  src="js/superfish-1.4.8/js/superfish.js"></script>
    <script  src="js/superfish-1.4.8/js/supersubs.js"></script>
    <!-- ENDS superfish -->

    <!-- poshytip -->
    <link rel="stylesheet" href="js/poshytip-1.1/src/tip-twitter/tip-twitter.css"  />
    <link rel="stylesheet" href="js/poshytip-1.1/src/tip-yellowsimple/tip-yellowsimple.css"  />
    <script  src="js/poshytip-1.1/src/jquery.poshytip.min.js"></script>
    <!-- ENDS poshytip -->


    <!-- GOOGLE FONTS -->
    <link href='http://fonts.googleapis.com/css?family=Allan:700' rel='stylesheet' type='text/css'>

    <!-- Flex Slider -->
    <link rel="stylesheet" href="css/flexslider.css" >
    <script src="js/jquery.flexslider-min.js"></script>
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
    <link rel="stylesheet" media="all" href="css/lessgrid.css"/>

    <!-- modernizr -->
    <script src="js/modernizr.js"></script>
	
  </head>
	
  <body lang="en">
	
    <!-- mobile-nav -->
    <div id="mobile-nav-holder">
    <div class="wrapper">
      <ul id="mobile-nav">
        <li  class="current-menu-item"><a href="home.jsp">home</a></li>
	<li><a href="page.html">about</a>
	</li>
	  <li><a href="settings.html">settings</a></li>
	  <li><a href="logout.html">logout</a></li>
        </ul>
      <div id="nav-open"><a href="#">Menu</a></div>
      </div>
    </div>
    <!-- ENDS mobile-nav -->
			
 <header>
			
				
    <div class="wrapper">

    <a href="home.jsp" id="logo"><img  src="img/logo.png" alt="Tandem"></a>

		<nav>
	<ul id="nav" class="sf-menu">
	<li><a href="upload.jsp">upload<span class="subheader">search music</span></a></li>
<li class="current-menu-item"><a href="home.jsp">welcome,<% SecurityController username = new SecurityController();   
                    String name = username.getCookieValue(request.getCookies()); 
                    if (name == null){  %>
                      <jsp:forward page="login.jsp">
                        <jsp:param name="Error" value="NoLogin"/>
                      </jsp:forward>
                    <%}
                    out.println(name);
                    %>
            <span class="subheader">nice to have you back</span></a>
    <ul>
    <li><a href="settings.html">Settings</a></li>
    <li><a href="Logout.do">Logout</a></li>
    </ul>
</li>
	
</ul>
</nav>
			
<div class="clearfix"></div>
		
	</div>
</header>

	
<!-- MAIN -->
<div id="main">
			
	<!-- social -->
	<div id="social-bar">
	<ul>
<li><a href="http://www.facebook.com"  title="Become a fan"><img src="img/social/facebook_32.png"  alt="Facebook" /></a></li>
<li><a href="http://www.twitter.com" title="Follow my tweets"><img src="img/social/twitter_32.png"  alt="Facebook" /></a></li>
<li><a href="http://www.google.com"  title="Add to the circle"><img src="img/social/google_plus_32.png" alt="Facebook" /></a></li>
	</ul>
</div>
<!-- ENDS social -->
			
<div id="about" class="nano">	
  
<!-- Content -->
<div id="content">
	
  <!-- slider -->
  <div class="results">

      <iframe src="results.jsp" title="Your searches" name="result_frame" style="width: 30%; border: solid #bbb thin; min-height: 600px;"></iframe>
      <iframe src="posresults.jsp" name="possible_frame" style="width: 30%; border: solid #bbb thin; min-height: 600px;"></iframe>
      <iframe src="lastfmresults.jsp" name="lastfm_frame" style="width: 30%; border: solid #bbb thin; min-height: 600px;"></iframe>

  </div>
  
  </div>

  <div class="clearfix"></div>
  </div>
</div>
<!-- ENDS content -->
			
<div class="clearfix"></div>
<div class="shadow-main"></div>
			
			
<!-- ENDS MAIN -->

  <footer>
  <div class="wrapper">
  <ul id="footer-cols">

  <li class="first-col">

  <div class="widget-block">
  </div>
 </li>
					
  <li class="second-col">
						
  <div class="widget-block">
</div>
		         		
</li>	
</ul>				
<div class="clearfix"></div>
</div>
	
<div id="to-top"></div>
</footer>
    <script type="text/javascript" src="js/jquery.nanoscroller.min.js"></script>
    <script type="text/javascript" src="js/main.js"></script>
</body>
	
</html>