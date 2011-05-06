/**
 * 
 */
package csillag.controller;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
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
 * @author Bognár Szabolcs, Hargitai Dávid
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
		 * ide esetleg be lehet venni az aktuális felhasználót, mint a tickethez rendelt
		 * felelõst, meg esetleg külön megadni a mérföldkövet...
		 */
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
		session.beginTransaction();        
		session.createQuery("insert into Ticket values ("+T+")");
		session.getTransaction().commit();
	}
	
	// idõrendben listáz
	@SuppressWarnings("unchecked")
	public List<Ticket> getSortedTickets()
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		List<Ticket> osszesTicket = session.createQuery("from Ticket order by letrehozva").list();
		session.getTransaction().commit();
		
		return osszesTicket;
	}
	
	// adott felhasználó ticketjeit listázza ki
	@SuppressWarnings("unchecked")
	public List<Ticket> getTicketsByFelhasznalo(Felhasznalo f)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		List<Ticket> ticketek = session.createQuery("from Ticket where felelos="+f.getFelhasznalonev()).list();
		session.getTransaction().commit();
		
		return ticketek;
	}
	
	// adott mérföldkõ alatti ticketeket listázza ki
	@SuppressWarnings("unchecked")
	public List<Ticket> getTicketsByMerfoldko(Merfoldko m)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		List<Ticket> ticketek = session.createQuery("from Ticket where merfoldko="+m.getNev()).list();
		session.getTransaction().commit();
		
		return ticketek;
	}
	
	// ha egy ticket állatota megváltozik
	public void stateChanged(Ticket t, byte allapot)
	{
		t.setAllapot(allapot);
	}
	
	// ha a ticket felelõse megváltozik
	public void responseChanged(Ticket t, Felhasznalo felelos)
	{
		t.setFelelos(felelos);
	}
	
	// ha a ticket másik mérföldkõ alá kerül
	public void milestoneChanged(Ticket t, Merfoldko merfoldko)
	{
		t.setMerfoldko(merfoldko);
	}
	
	// ha csatolás történik tickethez
	public void attachmentsAdded(Ticket t, Set<Csatolmany> csatolmanyok)
	{
		t.setCsatolmanyok(csatolmanyok);
	}
	
	// ha csatolás törlése történik ticketnél
	public void attachmentsDeleted(Ticket t, Set<Csatolmany> csatolmanyok)
	{
		if(t.getCsatolmanyok().containsAll(csatolmanyok))
		{
			t.getCsatolmanyok().removeAll(csatolmanyok);
		}
		else
		{
			// ide talán valami kiíratás kellhetne... vagy úgyis mindegy; ha nem hajtódik végre, nem okoz bajt...
			return;
		}
	}
	
	public String getLetrehozasIdopont(Ticket t, String formatum)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

		DateFormat df = new SimpleDateFormat(formatum);
        String hatarido = df.format(t.getLetrehozva());
        
        session.getTransaction().commit();
        
		return hatarido;
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
		
		// Nem zárjuk a sessiont, kell még a mérföldkőlistához is
		
		return dolgozok;
	}
	
	public List<Merfoldko> getMerfoldkovek()
	{
		return MerfoldkoController.getAllMilestones();
	}
	
	public void save(HttpServletRequest r)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        if (r.getParameter("id") == null)
        {
        	
        	Ticket t = new Ticket(r.getParameter("cim"), r.getParameter("leiras"), Byte.valueOf(r.getParameter("fontossag")), Ticket.UJ, new Date());
        	
        	Felhasznalo fh = (Felhasznalo)session.createQuery("from Felhasznalo where nev = 'nincs'").uniqueResult();
        	t.setFelelos(fh);
        	
        	Merfoldko m = (Merfoldko)session.createQuery("from Merfoldko where nev = 'nincs'").uniqueResult();
        	t.setMerfoldko(m);
        	
        	session.save(t);
        	
        }
        else
        {
        	
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
            
            
            if ( ! "0".equals(r.getParameter("merfoldko")))
            {
            	Merfoldko m = (Merfoldko)session.load(Merfoldko.class, Long.valueOf(r.getParameter("merfoldko")));
            	t.setMerfoldko(m);
            }
            else
            {
            	Merfoldko m = (Merfoldko)session.createQuery("from Merfoldko where nev = 'nincs'").uniqueResult();
            	t.setMerfoldko(m);
            }
        	
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
