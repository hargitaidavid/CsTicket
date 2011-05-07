package csillag.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
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
 * A ticketekkel kapcsolatos kéréseket kiszolgáló osztály.
 * 
 * @author Bognár Szabolcs, Hargitai Dávid
 *
 */
public class TicketController {

	@SuppressWarnings("unchecked")
	public List<Ticket> getAllTickets(String szuro)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		List<Ticket> osszesTicket;
		if (szuro.equals("osszes")) osszesTicket = session.createQuery("from Ticket").list();
		else if (szuro.equals("aktiv")) osszesTicket = session.createQuery("from Ticket where allapot in (:uj, :modositva)").setParameter("uj", Ticket.UJ).setParameter("modositva", Ticket.MODOSITVA).list();
		else osszesTicket = session.createQuery("from Ticket where allapot in (:megoldva, :nemmegoldva, :torolve)").setParameter("megoldva", Ticket.MEGOLDVA).setParameter("nemmegoldva", Ticket.NEM_LESZ_MEGOLDVA).setParameter("torolve", Ticket.TOROLVE).list();
		
		session.getTransaction().commit();
		
		return osszesTicket;
	}
	
	// adott felhasználó ticketjeit listázza ki
	public Set<Ticket> getTicketsByFelhasznalo(Long fid, String szuro)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        Felhasznalo f = (Felhasznalo)session.load(Felhasznalo.class, fid);
		Set<Ticket> ticketek = f.getTicketek();
		Set<Ticket> szurtTicketek = new HashSet<Ticket>();
		
		if ( ! szuro.equals("osszes") )
		{
			byte allapot;
			Iterator<Ticket> i = ticketek.iterator();
			while( i.hasNext() )
			{
				Ticket t = (Ticket)i.next();
				allapot = t.getAllapot();
				if (szuro.equals("aktiv"))
				{
					if (allapot == Ticket.UJ || allapot == Ticket.MODOSITVA)
					{
						szurtTicketek.add(t);
					}
				}
				else if (szuro.equals("lezart"))
				{
					if (allapot == Ticket.MEGOLDVA ||
						allapot == Ticket.NEM_LESZ_MEGOLDVA ||
						allapot == Ticket.TOROLVE)
					{
						szurtTicketek.add(t);
					}
				}
			}
			
			return szurtTicketek;
		}
		else
		{
			return ticketek;
		}
	}
	
	// adott mérföldkõ alatti ticketeket listázza ki
	public Set<Ticket> getTicketsByMerfoldko(Long mid, String szuro)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        Merfoldko m = (Merfoldko)session.load(Merfoldko.class, mid);
		Set<Ticket> ticketek = m.getTicketek();
		Set<Ticket> szurtTicketek = new HashSet<Ticket>();
		
		if ( ! szuro.equals("osszes") )
		{
			byte allapot;
			Iterator<Ticket> i = ticketek.iterator();
			while( i.hasNext() )
			{
				Ticket t = (Ticket)i.next();
				allapot = t.getAllapot();
				if (szuro.equals("aktiv"))
				{
					if (allapot != Ticket.UJ && allapot != Ticket.MODOSITVA)
					{
						szurtTicketek.remove(t);
					}
				}
				else if (szuro.equals("lezart"))
				{
					if (allapot != Ticket.MEGOLDVA && allapot != Ticket.NEM_LESZ_MEGOLDVA && allapot != Ticket.TOROLVE)
					{
						szurtTicketek.remove(t);
					}
				}
			}
			
			return szurtTicketek;
		}
		else
		{
			return ticketek;
		}
	}
	
	public static String getLetrehozasIdopont(Ticket t, String formatum)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

		DateFormat df = new SimpleDateFormat(formatum);
        String hatarido = df.format(t.getLetrehozva());
        
        session.getTransaction().commit();
        
		return hatarido;
	}
	
	public static Ticket getObject(String id)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		Ticket t = (Ticket)session.load(Ticket.class, Long.valueOf(id));
		
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Felhasznalo> getDolgozok()
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		List<Felhasznalo> dolgozok = session.createQuery("from Felhasznalo where dolgozo=1 and nev != 'nincs'").list();
		
		// Nem zárjuk a sessiont, kell még a mérföldkőlistához is
		
		return dolgozok;
	}
	
	public static void save(HttpServletRequest r)
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
        	
        	Ticket t = TicketController.getObject(r.getParameter("id"));

            t.setCim(r.getParameter("cim"));
            t.setAllapot(Byte.valueOf(r.getParameter("allapot")));
            t.setFontossag(Byte.valueOf(r.getParameter("fontossag")));
            t.setLeiras(r.getParameter("leiras"));
                        
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
	
	public static void delete(HttpServletRequest r)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        Ticket t = (Ticket)session.load(Ticket.class, Long.valueOf(r.getParameter("id")));
        
        t.getCsatolmanyok().clear();
        session.delete(t);
        
        session.getTransaction().commit();
	}
	
	public static void deleteCsatolmanyt(HttpServletRequest r)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        Ticket t = (Ticket)session.load(Ticket.class, Long.valueOf(r.getParameter("id")));
        Csatolmany cs = (Csatolmany)session.load(Csatolmany.class, Long.valueOf(r.getParameter("csid")));
        
        t.getCsatolmanyok().remove(cs);
        
        session.getTransaction().commit();
	}
	
}
