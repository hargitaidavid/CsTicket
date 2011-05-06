<%@page contentType="applicaton/octet-stream" import="csillag.controller.CsatolmanyController,csillag.model.Csatolmany" %><%@
page import="java.io.InputStream" %><%@
page import="java.io.ByteArrayOutputStream" %><%@
page import="java.sql.Blob" %><%

	Csatolmany cs = CsatolmanyController.getFajl(request.getParameter("id"));
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	byte[] buf = new byte[1024];
	Blob blob = cs.getAdat();

	InputStream in = blob.getBinaryStream();

	int n = 0;
	while( (n = in.read(buf)) >= 0 )
	{
		baos.write(buf, 0, n);
	}

	in.close();
	byte[] adat = baos.toByteArray();

	response.setContentType("applicaton/octet-stream");
	response.addHeader("Content-Disposition", "attachment; filename="+ cs.getCim() );
	response.setContentLength( (int) adat.length );
	response.getOutputStream().write(adat);
%>