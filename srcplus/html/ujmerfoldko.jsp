<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" import="java.util.*,csillag.model.*" %>

<!DOCTYPE html>

<html lang="hu-HU">
	<head>
		<meta charset="utf-8">
		<title>Ticketek - Csillag Ticketkezelő</title>
		<meta name="author" content="Bognár Szabolcs" />
		<meta name="author" content="Hargitai Dávid" />
		
		<script src="js/modernizr-1.7.min.js"></script><!-- this is the javascript allowing html5 to run in older browsers -->

	    <link rel="stylesheet" type="text/css" href="css/reset.css" media="screen" title="html5doctor.com Reset Stylesheet" />

	    <!-- in the CSS3 stylesheet you will find examples of some great new features CSS has to offer -->
	    <link rel="stylesheet" type="text/css" href="css/css3.css" media="screen" />

	    <!-- general stylesheet contains some default styles, you do not need this, but it helps you keep a uniform style -->
	    <link rel="stylesheet" type="text/css" href="css/general.css" media="screen" />

	    <!-- grid's will help you keep your website appealing to your users, view 52framework.com website for documentation -->
	    <link rel="stylesheet" type="text/css" href="css/grid.css" media="screen" />
	    
	    <link rel="stylesheet" type="text/css" href="css/forms.css" media="screen" />
	    
		<link rel="stylesheet" href="css/stilusok.css" />
		<link rel="stylesheet" href="css/tabs.css" />
		
		<link rel="stylesheet" type="text/css" href="css/anytimec.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/anytimec.js"></script>
	</head>
	
	<body>
	
		<jsp:useBean id="mrfldk" scope="session" class="csillag.controller.MerfoldkoController" />
		<jsp:setProperty name="mrfldk" property="*" />

		<%
		if( session.getAttribute("jog") == null || Integer.parseInt(session.getAttribute("jog").toString()) < Felhasznalo.MODERATOR )
		{
			response.sendRedirect("merfoldkovek.jsp");
		}
		else if( request.getParameter("nev") != null )
		{
			mrfldk.save(request);
			response.sendRedirect("merfoldkovek.jsp");
		}
		%>
		
		<div class="row">
			<header>
				<p class="logo col_7 col">Csillag Ticketkezelő</p>
			
				<nav class="col_9 col">
					<% if (session.getAttribute("nev") != null) { %>
					<p id="belepesLink">Bejelentkezve: <strong><%= session.getAttribute("nev") %></strong></p>
					<% } else { %>
					<p id="belepesLink"><a href="belepes.jsp" class="col_9 col">Belépés</a></p>
					<% } %>
				
					<ul>
						<li><a href="ujticket.jsp">Új ticket</a></li>
						<li><a href="index.jsp">Ticketek</a></li>
						<% if( session.getAttribute("jog") != null && Integer.parseInt(session.getAttribute("jog").toString()) >= Felhasznalo.MODERATOR ) { %>
						<li><a href="ujmerfoldko.jsp" class="active">Új mérföldkő</a></li>
						<% } %>
						<li><a href="merfoldkovek.jsp">Mérföldkövek</a></li>
					</ul>
				</nav>
			
				 <div class="clear"></div>
			</header>
		</div>
		
		<section class="row">
			<div class="col_10 col">
			
				<h1>Új mérföldkő létrehozása</h1>
			
				<form class="col col_7" action="ujmerfoldko.jsp" method="post">
					<fieldset>	
						<legend>Adatok</legend>
				    	<div>
				    		<label>Név</label>
				        	<input type="text" name="nev" required="required" class="box_shadow" />
				    	</div>
				    	<div>
				    		<label>Határidő</label>
				    	    <input type="text" id="hatarido" name="hatarido" required="required" class="box_shadow" />
				    	    <script type="text/javascript">
								$("#hatarido").AnyTime_picker(
							    { format: "%Y-%m-%d" } );
							</script>
				    	</div>

				    	<input type="submit" value="Létrehozás" />

					</fieldset>
				</form>
			
			</div>
		</section>
			
	
	</body>
</html>