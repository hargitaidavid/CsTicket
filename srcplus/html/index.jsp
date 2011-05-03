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
		<link rel="stylesheet" href="css/stilusok.css" />
	</head>
	
	<body>
	
		<jsp:useBean id="tckt" scope="session" class="csillag.controller.TicketController" />
		<jsp:setProperty name="tckt" property="*" />
		<%@ page import="java.util.*,csillag.model.*" %>
		
		<div class="row">
			<header>
				<p class="logo col_7 col">Csillag Ticketkezelő</p>
			
				<nav class="col_9 col">
					<a href="belepes.jsp" class="col_9 col" id="belepesLink">Belépés</a>
				
					<ul>
						<li><a href="ujticket.jsp">Új ticket</a></li>
						<li><a href="index.jsp">Ticketek</a></li>
						<li><a href="merfoldkovek.jsp">Mérföldkövek</a></li>
					</ul>
				</nav>
			
				 <div class="clear"></div>
			</header>
		</div>
		
		<section class="row">
			<div class="col_16 col">
				<h1>Ticketek listája</h1>
				<table>
					<tr>
						<th>Cím</th>
						<th>Leírás</th>
						<th>Fontosság</th>
						<th>Állapot</th>
						<th>Létrehozva</th>
					</tr>
			
				<% for(Object t : tckt.getOsszesTicket()) { %>
					<tr>
						<td><%= ((Ticket)t).getCim() %></td>
						<td><%= ((Ticket)t).getLeiras() %></td>
						<td><%= ((Ticket)t).getFontossag() %></td>
						<td><%= ((Ticket)t).getAllapot() %></td>
						<td><%= ((Ticket)t).getLetrehozva() %></td>
					</tr>		
				<% } %>
				</table>
			</div>
		</section>
			
	
	</body>
</html>