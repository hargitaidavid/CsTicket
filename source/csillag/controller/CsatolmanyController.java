/**
 * 
 */
package csillag.controller;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.hibernate.Session;

import csillag.model.Csatolmany;
import csillag.util.HibernateUtil;

/**
 * @author hargitaidavid
 *
 */
public class CsatolmanyController {

	public static Csatolmany mentes(String fajlnev)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		File fajl = new File(fajlnev);
        byte[] bFajl = new byte[(int) fajl.length()];
 
        try {
        	FileInputStream fileInputStream = new FileInputStream(fajl);
        	fileInputStream.read(bFajl);
        	fileInputStream.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        Blob blob = null;
        try {
			blob = new SerialBlob(bFajl);
		} catch (SerialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        int sep = fajlnev.lastIndexOf(System.getProperty("file.separator"));
        String cim = fajlnev.substring(sep + 1);
        
        Csatolmany cs = new Csatolmany();
        cs.setCim(cim);
        cs.setAdat(blob);
 
        session.save(cs);
        
        return cs;
	}
	
	
	public static Csatolmany getFajl(String csid)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		Csatolmany cs = (Csatolmany)session.load(Csatolmany.class, Long.valueOf(csid));
        
        return cs;
	}
	
}
