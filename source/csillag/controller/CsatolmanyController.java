package csillag.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.hibernate.Session;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;

import csillag.model.Csatolmany;
import csillag.model.Ticket;
import csillag.util.HibernateUtil;

/**
 * A csatolmányok kezelését végző osztály.
 *  
 * @author Bognár Szabolcs, Hargitai Dávid
 */
public class CsatolmanyController {
	
	public static Csatolmany getFajl(String csid)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		Csatolmany cs = (Csatolmany)session.load(Csatolmany.class, Long.valueOf(csid));
        
        return cs;
	}
	
	
	public static int mentes(HttpServletRequest r) throws IOException
	{
		String tid, cim;
		tid = "";
		cim = "";
		Blob adat = null;
		
		MultipartParser mp = new MultipartParser(r, 16*1024*1024);
    	Part part;
    	while( (part = mp.readNextPart()) != null )
    	{
    		String name = part.getName();
			if (part.isParam())
			{
				// it's a parameter part
				ParamPart paramPart = (ParamPart) part;
				String value = paramPart.getStringValue();
				if (name.equals("id")) tid = value;
			}
			else if (part.isFile())
			{
				// it's a file part
				FilePart filePart = (FilePart) part;
				String fileName = filePart.getFileName();
				if (fileName != null)
				{
					// the part actually contained a file
					InputStream in = filePart.getInputStream();
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					final int BUF_SIZE = 1 << 10 << 3; // 8KiB buffer size
					byte[] buffer = new byte[BUF_SIZE];
					int bytesRead = -1;
					while ((bytesRead = in.read(buffer)) > -1)
					{
						out.write(buffer, 0, bytesRead);
					}
					
			        try {
						adat = new SerialBlob(out.toByteArray());
					} catch (SerialException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
	                cim = fileName;
	                
				}
			}
    	}
    	
    	if (cim == "") return 0;
    	else
    	{
    		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            
            Ticket t = (Ticket)session.load(Ticket.class, Long.valueOf(tid));
            
    		Csatolmany cs = new Csatolmany();
    		cs.setCim(cim);
    		cs.setAdat(adat);
    		cs.setTartalmazo(t);
    		session.save(cs);
    		
    		session.getTransaction().commit();
    		
    		return t.getId().intValue();
    	}
	}
	
}
