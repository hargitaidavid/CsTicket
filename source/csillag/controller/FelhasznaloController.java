/**
 * 
 */
package csillag.controller;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;

import csillag.model.Felhasznalo;
import csillag.model.Ticket;
import csillag.util.HibernateUtil;

/**
 * @author hargitaidavid
 *
 */
public class FelhasznaloController {

	public Felhasznalo getObject(Long id)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        Felhasznalo t = (Felhasznalo)session.load(Felhasznalo.class, id);
		
		return t;
	}
	
	public Long azonositva(HttpServletRequest r)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        if (session.createQuery("select 1 from Felhasznalo where nev="+r.getParameter("nev")+" and jelszo="+r.getParameter("jelszo")).list().isEmpty())
        {
        	return 0L;
        }
        else
        {
        	
        	Felhasznalo f = (Felhasznalo)session.createQuery("from Felhasznalo where nev="+r.getParameter("nev")+" and jelszo="+r.getParameter("jelszo")).uniqueResult();
			return f.getId();
			
		}
	}
	
}
