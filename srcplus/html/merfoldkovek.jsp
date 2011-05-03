<!DOCTYPE html>

<html lang="hu-HU">
	<head>
		<meta charset="utf-8" />
		<title>Csillag Ticketkezelő</title>
		<meta name="author" content="Bognár Szabolcs" />
		<meta name="author" content="Hargitai Dávid" />
		<link rel="stylesheet" href="css/stilus.css" />
	</head>
	
	<body>
	
		<jsp:useBean id="tckt" scope="session" class="csillag.controller.TicketController" />
		<jsp:setProperty name="tckt" property="*" />
		<%@ page import="java.util.*,csillag.model.*" %>
		
		<header>
			<p id="brand">Csillag Ticketkezelő</p>
			
			<nav>
				<a href="belepes.jsp" id="belepesLink">Belépés</a>
				
				<ul>
					<li><a href="ujticket.jsp">Új ticket</a></li>
					<li><a href="index.jsp" class="active">Ticketek</a></li>
					<li><a href="merfoldkovek.jsp">Mérföldkövek</a></li>
				</ul>
			</nav>
		</header>
		
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
		</ul>
			
	
	</body>
</html>