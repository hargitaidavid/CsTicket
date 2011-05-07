<jsp:useBean id="csat" scope="session" class="csillag.controller.CsatolmanyController" /><jsp:setProperty name="csat" property="*" /><%
	int tid = 0;
	tid = csat.mentes(request);
	
	if (tid == 0) response.sendRedirect("index.jsp");
	else response.sendRedirect("index.jsp?id="+tid);
%>