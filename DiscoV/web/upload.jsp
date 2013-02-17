v<!doctype html>
<html class="no-js">
  <head>
    <%@page import="cookies.SecurityController"%>
    <meta charset="utf-8"/>
    <link href='http://fonts.googleapis.com/css?family=Crafty+Girls' rel='stylesheet' type='text/css'>
    <title>Skratch</title>


    <!--[if lt IE 9]>
    <script src="js/css3-mediaqueries.js"></script>
    <![endif]-->

    <link rel="stylesheet" media="all" href="css/style.css"/>
	<link rel="stylesheet" media="all" href="css/upload.css"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <script src="js/jquery-1.6.4.min.js"></script>
	<script src="js/upload.js"></script>
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

    <!-- prettyPhoto -->
    <script  src="js/prettyPhoto/js/jquery.prettyPhoto.js"></script>
    <link rel="stylesheet" href="js/prettyPhoto/css/prettyPhoto.css"  media="screen" />
    <!-- ENDS prettyPhoto -->

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
	<ul>
	</ul>
	</li>
	  <li><a href="contact.html">contact</a></li>
	  <li><a href="http://luiszuno.com/blog/downloads/modus-html-template">logout</a></li>
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
	<li class="current-menu-item"><a href="home.jsp">home<span class="subheader">welcome</span></a></li>

<li><a href="home.jsp">welcome,<% SecurityController username = new SecurityController();   
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
	<li><a href="logout.html">Logout</a></li>
    </ul>
</li>
	
</ul>
</nav>
			
<div class="clearfix"></div>
		
	</div>
</header>

	
<!-- MAIN -->
<div id="main">
		
<!-- Content -->
<div id="content">
	
</head>
<!-- upload -->
       <div class="upload_form_cont">
       <form id="upload_form" enctype="multipart/form-data" method="post" action="Upload.do">
       <div>
       <div><label for="image_file">Please select an audio file</label></div>
       <div><input type="file" name="image_file" id="image_file" onchange="fileSelected();" /></div>
       </div>
       <div>
       <input type="submit" value="Upload" onclick="startUploading()" />
        <input type="button" value="Cancel" onclick="uploadAbort()" />
              </div>
              <div id="fileinfo">
                  <div id="filename"></div>
                   <div id="filesize"></div>
                    <div id="filetype"></div>
                  <div id="filedim"></div>
             </div>
           <div id="error">You should select valid audio files only!</div>
             <div id="error2">An error occurred while uploading the file</div>
          <div id="abort">The upload has been canceled by the user or the browser dropped the connection</div>
           <div id="warnsize">Your file is very big. We can't accept it. Please select more small file</div>
            <div id="progress_info">
             <div id="progress"></div>
              <div id="progress_percent">&nbsp;</div>
               <div class="clear_both"></div>
             <div>
              <div id="speed">&nbsp;</div>
               <div id="remaining">&nbsp;</div>
            <div id="b_transfered">&nbsp;</div>
             <div class="clear_both"></div>
                 </div>
                    <div id="upload_response"></div>
                </div>
              </form>
            </div>

		<!-- end upload -->
			<!-- ENDS content -->
			
			<div class="clearfix"></div>			
</body>
	
</html>