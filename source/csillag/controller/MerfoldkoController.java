/**
 * 
 */
package csillag.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;

import csillag.model.Merfoldko;
import csillag.model.Ticket;
import csillag.util.HibernateUtil;

/**
 * @author hargitaidavid
 *
 */
public class MerfoldkoController {
	
	@SuppressWarnings("unchecked")
	public List<Merfoldko> getAllMilestones()
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		List<Merfoldko> osszesMerfoldko = session.createQuery("from Merfoldko").list();
		session.getTransaction().commit();
		
		return osszesMerfoldko;
	}
	
	public int getTicketsNumberByState(Long id, byte s)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    
	    Merfoldko m = (Merfoldko)session.load(Merfoldko.class,id);
	    
		int szamlalo = 0;
		Set<Ticket> ticketek = m.getTicketek();
		Iterator<Ticket> it = ticketek.iterator();
		while(it.hasNext())
		{
			Ticket t = it.next();
			if(t.getAllapot() == s) szamlalo++;
		}
		
		session.getTransaction().commit();
		return szamlalo;
	}

	public int aktivTicketekSzama(Long id)
	{        
	    int sz = getTicketsNumberByState(id, Ticket.UJ) + getTicketsNumberByState(id, Ticket.MODOSITVA);
	    
		return sz;
	}

	public int lezartTicketekSzama(Long id)
	{
	    int sz = getTicketsNumberByState(id, Ticket.NEM_LESZ_MEGOLDVA);

		return sz;
	}
	
	public String getHatarido(Merfoldko m, String formatum)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

		DateFormat df = new SimpleDateFormat(formatum);
        String hatarido = df.format(m.getHatarido());
        
        session.getTransaction().commit();
        
		return hatarido;
	}
	
	public Merfoldko getObject(String id)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		Merfoldko m = (Merfoldko)session.load(Merfoldko.class, Long.valueOf(id));
		
		return m;
	}
	
	public void save(HttpServletRequest r)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		Merfoldko m = getObject(r.getParameter("id"));
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date hi = new Date();
		try {
            hi = df.parse(r.getParameter("hatarido"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        m.setNev(r.getParameter("nev"));
        m.setHatarido(hi);
        
        session.getTransaction().commit();
	}
	
}
