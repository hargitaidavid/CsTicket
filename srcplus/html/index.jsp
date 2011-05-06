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
	    
		<link rel="stylesheet" href="css/stilusok.css" />
		<link rel="stylesheet" href="css/tabs.css" />
	</head>
	
	<body>
	
		<jsp:useBean id="tckt" scope="session" class="csillag.controller.TicketController" />
		<jsp:setProperty name="tckt" property="*" />

		<%
		if( "torles".equals(request.getParameter("akcio")) )
		{
			tckt.delete(request);
			response.sendRedirect("index.jsp");
		}
		else if( request.getParameter("cim") != null )
		{
			tckt.save(request);
			response.sendRedirect("index.jsp");
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
						<li><a href="index.jsp" class="active">Ticketek</a></li>
						<li><a href="merfoldkovek.jsp">Mérföldkövek</a></li>
					</ul>
				</nav>
			
				 <div class="clear"></div>
			</header>
		</div>
		
		<section class="row">
			<div class="col_10 col">
			
			<% if( request.getParameter("id") == null || "torles".equals(request.getParameter("akcio")) ) { %>
			
				<h1>Ticketek listája</h1>
				
				<ul class="tabs">
					<li><a href="index.jsp" class="current">Időrendben</a></li>
					<li><a href="index.jsp?miszerint=felelos">Felelős szerint</a></li>
					<li><a href="index.jsp?miszerint=merfoldko">Mérföldkő szerint</a></li>
				</ul>
				
				<table>
					<tr>
						<th>Cím</th>
						<th>Leírás</th>
						<th>Fontosság</th>
						<th>Állapot</th>
						<th>Létrehozva</th>
					</tr>
			
				<% for(Object t : tckt.getAllTickets()) { %>
					<tr>
						<td><a href="index.jsp?id=<%= ((Ticket)t).getId() %>"><%= ((Ticket)t).getCim() %></a></td>
						<td><%= ((Ticket)t).getLeiras() %></td>
						<td><%= ((Ticket)t).getFontossag() %></td>
						<td><%= ((Ticket)t).getAllapot() %></td>
						<td><%= tckt.getLetrehozasIdopont((Ticket)t, "yyyy-MM-dd HH:mm") %></td>
					</tr>		
				<% } %>
				</table>
				
				
				
			<% } else {
				Ticket t = tckt.getObject(request.getParameter("id"));
			%>
			
			
			
			<h1><%= t.getCim() %> ticket adatai</h1>
			
			<form class="col col_7" action="index.jsp" method="post">
	    		<fieldset>	
	        		<legend>Adatok</legend>
	        		<input type="hidden" name="id" value="<%= t.getId() %>" />
	            	<div>
	            		<label>Cím</label>
	                	<input type="text" name="cim" value="<%= t.getCim() %>" required="required" class="box_shadow" />
	            	</div>
	            	
	            	<div>
	            		<label>Leírás</label>
	                	<textarea name="leiras" required="required" class="box_shadow"><%= t.getLeiras() %></textarea>
	            	</div>
	            	
	            	<div>
	            		<label>Fontosság</label>
	            		<select name="fontossag">
							<option<% if(t.getFontossag() == t.NAGYON_SURGOS){ %> selected="selected"<% } %> value="<%= t.NAGYON_SURGOS %>">Nagyon sürgős</option>
							<option<% if(t.getFontossag() == t.FONTOS){ %> selected="selected"<% } %> value="<%= t.FONTOS %>">Fontos</option>
							<option<% if(t.getFontossag() == t.NORMAL){ %> selected="selected"<% } %> value="<%= t.NORMAL %>">Normál</option>
							<option<% if(t.getFontossag() == t.RAER){ %> selected="selected"<% } %> value="<%= t.RAER %>">Ráér</option>
						</select>
					</div>
					
					<div>
	            		<label>Állapot</label>
	            		<select name="allapot">
							<option<% if(t.getAllapot() == t.UJ){ %> selected="selected"<% } %> value="<%= t.UJ %>">Új</option>
							<option<% if(t.getAllapot() == t.MEGOLDVA){ %> selected="selected"<% } %> value="<%= t.MEGOLDVA %>">Megoldva</option>
							<option<% if(t.getAllapot() == t.NEM_LESZ_MEGOLDVA){ %> selected="selected"<% } %> value="<%= t.NEM_LESZ_MEGOLDVA %>">Nem lesz megoldva</option>
							<option<% if(t.getAllapot() == t.MODOSITVA){ %> selected="selected"<% } %> value="<%= t.MODOSITVA %>">Módosítva</option>
							<option<% if(t.getAllapot() == t.TOROLVE){ %> selected="selected"<% } %> value="<%= t.TOROLVE %>">Törölve</option>
						</select>
					</div>
			
					<div>
	            		<label>Felelős</label>
	            		<select name="felelos">
	            			<option<% if( "nincs".equals(t.getFelelos().getNev()) ){ %> selected="selected"<% } %> value="0">-- Nincs --</option>
	            		<% for(Object f : tckt.getDolgozok()) { %>
							<option<% if( ((Felhasznalo)f).getId() == t.getFelelos().getId()){ %> selected="selected"<% } %> value="<%= ((Felhasznalo)f).getId() %>"><%= ((Felhasznalo)f).getNev() %></option>
						<% } %>
						</select>
					</div>
					
					<div>
	            		<label>Mérföldkő</label>
	            		<select name="merfoldko">
	            			<option<% if( "nincs".equals(t.getMerfoldko().getNev()) ){ %> selected="selected"<% } %> value="0">-- Nincs --</option>
	            		<% for(Object m : tckt.getMerfoldkovek()) { %>
							<option<% if( ((Merfoldko)m).getId() == t.getMerfoldko().getId()){ %> selected="selected"<% } %> value="<%= ((Merfoldko)m).getId() %>"><%= ((Merfoldko)m).getNev() %></option>
						<% } %>
						</select>
					</div>

					<% if( session.getAttribute("jog") != null && Integer.parseInt(session.getAttribute("jog").toString()) >= Felhasznalo.MODERATOR ) { %>
	            	<input type="submit" value="Mentés" />
	            	<% } %>
	            	<% if( session.getAttribute("jog") != null && Integer.parseInt(session.getAttribute("jog").toString()) >= Felhasznalo.ADMIN ) { %>
	            	<a href="index.jsp?id=<%= request.getParameter("id") %>&akcio=torles">Törlés</a>
	            	<% } %>

	        	</fieldset>
	    	</form>
			
			<% } %>
			</div>
			
			<% if( request.getParameter("id") == null || "torles".equals(request.getParameter("akcio")) ) { %>
			<div class="col_6 col">
				<ul class="szuro_gombok">
					<li><a href="index.jsp">Összes</a></li>
					<li><a href="index.jsp?szuro=aktiv">Aktív</a></li>
					<li><a href="index.jsp?szuro=lezart">Lezárt</a></li>
				</ul>
			</div>
			<% } %>
		</section>
			
	
	</body>
</html>