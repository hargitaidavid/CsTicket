<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%><!DOCTYPE html>

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
	    
		<link rel="stylesheet" href="css/stilusok.css" />
		<link rel="stylesheet" href="css/tabs.css" />
	</head>
	
	<body>
	
		<jsp:useBean id="usr" scope="session" class="csillag.controller.FelhasznaloController" />
		<jsp:setProperty name="usr" property="*" />
		<%@ page import="java.util.*,csillag.model.*" %>
		

		
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
						<li><a href="index.jsp" class="active">Ticketek</a></li>
						<li><a href="merfoldkovek.jsp">Mérföldkövek</a></li>
					</ul>
				</nav>
			
				 <div class="clear"></div>
			</header>
		</div>
		
		<section class="row">
			<div class="col_10 col">
			
		<%
		if (request.getParameter("nev") != null && request.getParameter("jelszo") != null)
		{
			Long id = usr.azonositva(request);
			if( id.intValue() > 0 )
			{
				Felhasznalo f = usr.getObject(id);
				
				session.setAttribute("nev", f.getNev());
				session.setAttribute("jog", f.getJogkor());
		 	%>
		
			<h1>Üdv, <%= f.getNev() %></h1>
		
			<%
			}
			else
			{ %>
			
			<h1>Helytelen felhasználónév vagy jelszó!</h1>
			
			<%} 
		} else {
			%>
			
			<h1>Belépés</h1>
			
			<form class="col col_7" action="belepes.jsp" method="post">
	    		<fieldset>	
	        		<legend>Adatok</legend>
	            	<div>
	            		<label>Név</label>
	                	<input type="text" name="nev" required="required" class="box_shadow" />
	            	</div>
	            	
	            	<div>
	            		<label>Jelszó</label>
	                	<input type="password" name="jelszo" required="required" class="box_shadow" />
	            	</div>
	            	
	            	<input type="submit" value="Belépés" />

	        	</fieldset>
	    	</form>
			
			<% } %>
			</div>
		</section>
			
	
	</body>
</html>