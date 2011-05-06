/**
 * 
 */
package csillag.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;

import csillag.model.Csatolmany;
import csillag.model.Felhasznalo;
import csillag.model.Merfoldko;
import csillag.model.Ticket;
import csillag.util.HibernateUtil;

/**
 * @author Bogn�r Szabolcs, Hargitai D�vid
 *
 */
public class TicketController {

	public List getAllTickets()
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		List osszesTicket = session.createQuery("from Ticket").list();
		session.getTransaction().commit();
		
		return osszesTicket;
	}
	
	public void addNewTicket(String cim, String leiras, byte fontossag)
	{
		Ticket T = new Ticket(cim, leiras, fontossag, Ticket.UJ, new Date());
		
		/*
		 * ide esetleg be lehet venni az aktu�lis felhaszn�l�t, mint a tickethez rendelt
		 * felel�st, meg esetleg k�l�n megadni a m�rf�ldk�vet...
		 */
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
		session.beginTransaction();        
		session.createQuery("insert into Ticket values ("+T+")");
		session.getTransaction().commit();
	}
	
	// id�rendben list�z
	@SuppressWarnings("unchecked")
	public List<Ticket> getSortedTickets()
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		List<Ticket> osszesTicket = session.createQuery("from Ticket order by letrehozva").list();
		session.getTransaction().commit();
		
		return osszesTicket;
	}
	
	// adott felhaszn�l� ticketjeit list�zza ki
	@SuppressWarnings("unchecked")
	public List<Ticket> getTicketsByFelhasznalo(Felhasznalo f)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		List<Ticket> ticketek = session.createQuery("from Ticket where felelos="+f.getFelhasznalonev()).list();
		session.getTransaction().commit();
		
		return ticketek;
	}
	
	// adott m�rf�ldk� alatti ticketeket list�zza ki
	@SuppressWarnings("unchecked")
	public List<Ticket> getTicketsByMerfoldko(Merfoldko m)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		List<Ticket> ticketek = session.createQuery("from Ticket where merfoldko="+m.getNev()).list();
		session.getTransaction().commit();
		
		return ticketek;
	}
	
	// ha egy ticket �llatota megv�ltozik
	public void stateChanged(Ticket t, byte allapot)
	{
		t.setAllapot(allapot);
	}
	
	// ha a ticket felel�se megv�ltozik
	public void responseChanged(Ticket t, Felhasznalo felelos)
	{
		t.setFelelos(felelos);
	}
	
	// ha a ticket m�sik m�rf�ldk� al� ker�l
	public void milestoneChanged(Ticket t, Merfoldko merfoldko)
	{
		t.setMerfoldko(merfoldko);
	}
	
	// ha csatol�s t�rt�nik tickethez
	public void attachmentsAdded(Ticket t, Set<Csatolmany> csatolmanyok)
	{
		t.setCsatolmanyok(csatolmanyok);
	}
	
	// ha csatol�s t�rl�se t�rt�nik ticketn�l
	public void attachmentsDeleted(Ticket t, Set<Csatolmany> csatolmanyok)
	{
		if(t.getCsatolmanyok().containsAll(csatolmanyok))
		{
			t.getCsatolmanyok().removeAll(csatolmanyok);
		}
		else
		{
			// ide tal�n valami ki�rat�s kellhetne... vagy �gyis mindegy; ha nem hajt�dik v�gre, nem okoz bajt...
			return;
		}
	}
	
	public Ticket getObject(String id)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		Ticket t = (Ticket)session.load(Ticket.class, Long.valueOf(id));
		
		return t;
	}
	
	public List<Felhasznalo> getDolgozok()
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		List<Felhasznalo> dolgozok = session.createQuery("from Felhasznalo where dolgozo=1 and nev != 'nincs'").list();
		session.getTransaction().commit();
		
		return dolgozok;
	}
	
	public void save(HttpServletRequest r)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		Ticket t = getObject(r.getParameter("id"));

        t.setCim(r.getParameter("cim"));
        t.setAllapot(Byte.valueOf(r.getParameter("allapot")));
        t.setFontossag(Byte.valueOf(r.getParameter("fontossag")));
        t.setLeiras(r.getParameter("leiras"));
        
        //t.setCsatolmanyok();
        
        if ( ! "0".equals(r.getParameter("felelos")))
        {
        	Felhasznalo fh = (Felhasznalo)session.load(Felhasznalo.class, Long.valueOf(r.getParameter("felelos")));
        	t.setFelelos(fh);
        }
        else
        {
        	Felhasznalo fh = (Felhasznalo)session.createQuery("from Felhasznalo where nev = 'nincs'").uniqueResult();
        	t.setFelelos(fh);
        }
        
        
        session.getTransaction().commit();
	}
	
	public void delete(HttpServletRequest r)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        Ticket t = (Ticket)session.load(Ticket.class, Long.valueOf(r.getParameter("id")));
        session.delete(t);
        
        session.getTransaction().commit();
	}
	
}
