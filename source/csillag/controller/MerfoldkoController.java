package csillag.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 * A m�rf�ldk�vekkel kapcsolatos k�r�seket kiszolg�l� oszt�ly.
 * 
 * @author Bogn�r Szabolcs, Hargitai D�vid
 *
 */
public class MerfoldkoController {
	
	@SuppressWarnings("unchecked")
	public static List<Merfoldko> getAllMilestones()
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		List<Merfoldko> osszesMerfoldko = new ArrayList<Merfoldko>();
		
		Iterator<Merfoldko> it = session.createQuery("from Merfoldko").list().iterator();
		while(it.hasNext())
		{
			Merfoldko m = it.next();
			if( ! "nincs".equals(m.getNev())) osszesMerfoldko.add(m);
		}
		
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
	    int sz = getTicketsNumberByState(id, Ticket.NEM_LESZ_MEGOLDVA) + getTicketsNumberByState(id, Ticket.MEGOLDVA);

		return sz;
	}
	
	public String getHatarido(Merfoldko m, String formatum)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

		DateFormat df = new SimpleDateFormat(formatum);
        String hatarido = df.format(m.getHatarido());
        
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
        
        if (r.getParameter("id") == null)
        {
        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    		Date hi = new Date();
    		try {
                hi = df.parse(r.getParameter("hatarido"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            
        	Merfoldko m = new Merfoldko(r.getParameter("nev"), hi);
        	session.save(m);
    		
        }
        else
        {
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
        }
        
        session.getTransaction().commit();
	}
	
	public void delete(HttpServletRequest r)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        Merfoldko m = (Merfoldko)session.load(Merfoldko.class, Long.valueOf(r.getParameter("id")));
        Merfoldko m0 = (Merfoldko)session.createQuery("from Merfoldko where nev = 'nincs'").uniqueResult();
        
        Set<Ticket> ticketek = m.getTicketek();
        m.getTicketek().clear();
        
		Iterator<Ticket> it = ticketek.iterator();
		while(it.hasNext())
		{
			Ticket t = it.next();
			t.setMerfoldko(m0);
		}
        
        session.delete(m);
        
        session.getTransaction().commit();
	}
	
	public void deleteTicket(HttpServletRequest r)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        Merfoldko m = (Merfoldko)session.load(Merfoldko.class, Long.valueOf(r.getParameter("id")));
        Merfoldko m0 = (Merfoldko)session.createQuery("from Merfoldko where nev = 'nincs'").uniqueResult();
        Ticket t = (Ticket)session.load(Ticket.class, Long.valueOf(r.getParameter("tid")));
        
        m.getTicketek().remove(t);
        t.setMerfoldko(m0);       
        
        if (m.getTicketek().size() < 1)
        {
        	session.getTransaction().commit();
        	session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
        }
	}
	
}
