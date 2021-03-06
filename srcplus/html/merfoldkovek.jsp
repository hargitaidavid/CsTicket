<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" import="java.util.*,csillag.model.*" %><!DOCTYPE html>

<html lang="hu-HU">
	<head>
		<meta charset="utf-8">
		<title>Mérföldkövek - Csillag Ticketkezelő</title>
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
		<jsp:useBean id="tckt" scope="session" class="csillag.controller.TicketController" />
		<jsp:setProperty name="tckt" property="*" />
		
		<%
		if( "torles".equals(request.getParameter("akcio")) )
		{
			mrfldk.delete(request);
			response.sendRedirect("merfoldkovek.jsp");
		}
		else if( "tickettorles".equals(request.getParameter("akcio")) )
		{
			mrfldk.deleteTicket(request);
			response.sendRedirect("merfoldkovek.jsp?id="+request.getParameter("id"));
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
						<li><a href="ujmerfoldko.jsp">Új mérföldkő</a></li>
						<% } %>
						<li><a href="merfoldkovek.jsp" class="active">Mérföldkövek</a></li>
					</ul>
				</nav>
			
				 <div class="clear"></div>
			</header>
		</div>
		
		<section class="row">
			<div class="col_10 col">
			
			<% if( request.getParameter("id") == null || "torles".equals(request.getParameter("akcio")) ) { %>
			
				<h1>Mérföldkövek listája</h1>
				
				
				<table>
					<tr>
						<th>Név</th>
						<th>Határidő</th>
						<th>Ticketek</th>
					</tr>
			
				<% for(Object m : mrfldk.getAllMilestones()) { %>
					<tr>
						<td><a href="merfoldkovek.jsp?id=<%= ((Merfoldko)m).getId() %>"><%= ((Merfoldko)m).getNev() %></a></td>
						<td><%= mrfldk.getHatarido((Merfoldko)m, "yyyy-MM-dd") %></td>
						<td><%= mrfldk.aktivTicketekSzama(((Merfoldko)m).getId()) %> aktív ticket<br>
							<%= mrfldk.lezartTicketekSzama(((Merfoldko)m).getId()) %> lezárt ticket
						</td>
					</tr>		
				<% } %>
				</table>
				
			<% } else {
				Merfoldko m = mrfldk.getObject(request.getParameter("id"));
			%>
			
				<h1><%= m.getNev() %> mérföldkő adatai</h1>

				<form class="col col_7" action="merfoldkovek.jsp" method="post">
					<fieldset>	
						<legend>Adatok</legend>
						<input type="hidden" name="id" value="<%= m.getId() %>" />
				    	<div>
				    		<label>Név</label>
				        	<input type="text" name="nev" value="<%= m.getNev() %>" required="required" class="box_shadow" />
				    	</div>
				    	<div>
				    		<label>Határidő ("yyyy-MM-dd")</label>
				    	    <input type="text" name="hatarido" id="hatarido" value="<%= mrfldk.getHatarido((Merfoldko)m, "yyyy-MM-dd") %>" required="required" class="box_shadow" />
				    		<script type="text/javascript">
								$("#hatarido").AnyTime_picker(
							    { format: "%Y-%m-%d" } );
							</script>
				    	</div>

				    	<% if( session.getAttribute("jog") != null && Integer.parseInt(session.getAttribute("jog").toString()) >= Felhasznalo.MODERATOR ) { %>
				    	<input type="submit" value="Mentés" />
				    	<% } %>
				    	<% if( session.getAttribute("jog") != null && Integer.parseInt(session.getAttribute("jog").toString()) >= Felhasznalo.ADMIN ) { %>
				    	<a href="merfoldkovek.jsp?id=<%= request.getParameter("id") %>&akcio=torles">Törlés</a>
						<% } %>

					</fieldset>
				</form>

				<% if( ! m.getTicketek().isEmpty() ) { %>
				<table>
					<caption>Ticketek</caption>
					<tr>
						<th>Cím</th>
						<th>Fontosság</th>
						<th>Állapot</th>
						<th>Létrehozva</th>
						<th>Művelet</th>
					</tr>
					<% for(Object t : m.getTicketek()) { %>
					<tr>
						<td><%= ((Ticket)t).getCim() %></td>
						<td><%= ((Ticket)t).getFontossag() %></td>
						<td><%= ((Ticket)t).getAllapot() %></td>
						<td><%= tckt.getLetrehozasIdopont((Ticket)t, "yyyy-MM-dd HH:mm") %></td>
						<td><a href="index.jsp?id=<%= ((Ticket)t).getId() %>">Részletek</a><br>
							<% if( session.getAttribute("jog") != null && Integer.parseInt(session.getAttribute("jog").toString()) >= Felhasznalo.MODERATOR ) { %>
							<a href="merfoldkovek.jsp?id=<%= request.getParameter("id") %>&akcio=tickettorles&tid=<%= ((Ticket)t).getId() %>">Törlés</a>
							<% } %>
						</td>
					</tr>	
					<% } %>
				</table>
				<% } %>
			
			<% } %>
			</div>
			
		</section>
			
	
	</body>
</html>