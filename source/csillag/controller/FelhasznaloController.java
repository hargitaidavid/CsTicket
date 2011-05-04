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
        
        boolean s = session.createQuery("from Felhasznalo where felhasznalonev = :nev and jelszo = :jelszo")
        	.setParameter("nev", r.getParameter("nev"))
        	.setParameter("jelszo", r.getParameter("jelszo"))
        	.list()
        	.isEmpty();
        
        Felhasznalo f;
        
        if( s )
        {
        	return new Long(0);
        }
        else
        {
        	f = (Felhasznalo)session
        	.createQuery("from Felhasznalo where felhasznalonev = :nev and jelszo = :jelszo")
        	.setParameter("nev", r.getParameter("nev"))
        	.setParameter("jelszo", r.getParameter("jelszo"))
        	.uniqueResult();
        	
        	return f.getId();
		}
	}
	
}
