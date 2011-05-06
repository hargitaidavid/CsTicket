package csillag;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import org.hibernate.*;


import csillag.util.HibernateUtil;
import csillag.model.*;

/**
 * Adatbázis feltöltése mintaadatokkal a kezdéshez
 *  
 * @author Bognár Szabolcs, Hargitai Dávid
 */
public class FrontServlet extends HttpServlet {

	private static final long serialVersionUID = 4738558675008114342L;
	public static final String HEADER = "<html>"+
        "<head>"+
        "<title>Csillag Ticketkezelő</title>"+
        "</head>"+
        "<body bgcolor=\"white\">";
    public static final String FOOTER = "<a href=\"index.jsp\">a ticketek listajahoz</a>"+
        "</body>"+
        "</html>";

	/**
	 * Ezzel a metodussal ellenorizzuk, hogy ures-e az adatbazis
	 */
    public boolean isEmptyDB() {
    	// TODO: megoldani
    	boolean ures = true;
    	
  		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        ures = session.createQuery("select 1 from Ticket").list().isEmpty();
        
        session.getTransaction().commit();
    	
    	return ures; 
	}

	/**
	 * Ez a metodus vegzi el az inicializalast.
	 */
    public void initializeDB() {
    	// TODO: megoldani
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	Date hi1,hi2,hi3;
    	
    	hi1 = hi2 = hi3 = new Date();
    	
        try {
        	hi1 = df.parse("2011-11-23");
        	hi2 = df.parse("2010-11-23");
        	hi3 = df.parse("2011-08-03");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        Merfoldko m0,m1,m2,m3;
        
        m0 = new Merfoldko("nincs", null);
    	session.save(m0);
        
        m1 = new Merfoldko("Beta valtozat", hi1);
    	session.save(m1);
    	
    	m2 = new Merfoldko("Kiadasra jelolt 1", hi2);
    	session.save(m2);
    	
    	m3 = new Merfoldko("Alfa valtozat beta valtozata", hi3);
    	session.save(m3);
    	
        
    	Ticket t1,t2,t3,t4,t5,t6,t7,t8,t9;
    	t1 = new Ticket("Fooldali hiba", "Hiba van a fooldalon, meg kene javitani", Ticket.NORMAL, Ticket.UJ, new Date());
    	session.save(t1);
    	
    	t2 = new Ticket("Elromlott a gaztuzhely", "A gaztuzhely surgos javitasra szorul", Ticket.FONTOS, Ticket.UJ, new Date());
    	session.save(t2);
    	
    	t3 = new Ticket("Oldal hatterszin", "Nem szep a bejelentkezo oldal szine", Ticket.RAER, Ticket.UJ, new Date());
    	session.save(t3);
    	
    	t4 = new Ticket("Vegzetes hiba", "Kiborult a kave a tortenelemkonyvre", Ticket.NORMAL, Ticket.UJ, new Date());
    	session.save(t4);
    	
    	t5 = new Ticket("Baleset tortent", "Hazafele nem figyelt egy arok meg elenk ugrott egy padka", Ticket.FONTOS, Ticket.UJ, new Date());
    	session.save(t5);
    	
    	t6 = new Ticket("Betutipus", "Ennel szerencsetlenebb valasztas nehez lett volna", Ticket.RAER, Ticket.UJ, new Date());
    	session.save(t6);
    	
    	t7 = new Ticket("Kapufa", "...es itt a vege a meccsnek", Ticket.NORMAL, Ticket.UJ, new Date());
    	session.save(t7);
    	
    	t8 = new Ticket("Mennyi kell meg?", "Jaj de unom", Ticket.FONTOS, Ticket.UJ, new Date());
    	session.save(t8);
    	
    	t9 = new Ticket("Jo sok okorseget leirok", "De ide meg tobbet kene, csak mar nincs kedvem", Ticket.RAER, Ticket.UJ, new Date());
    	session.save(t9);
    	
    	
    	Felhasznalo u0,u1,u2,u3,u4;
    	
    	u0 = new Felhasznalo("nincs", "nincs", "nincs", Felhasznalo.FELHASZNALO, true);
    	session.save(u0);
    	
    	u1 = new Felhasznalo("admin", "admin", "Admini Sztrator", Felhasznalo.ADMIN, false);
    	session.save(u1);
    	
    	u2 = new Felhasznalo("moderator", "moderator", "Moder Ator", Felhasznalo.MODERATOR, true);
    	session.save(u2);
    	
    	u3 = new Felhasznalo("munkas1", "munkas1", "Balog Bela", Felhasznalo.FELHASZNALO, true);
    	session.save(u3);
    	
    	u4 = new Felhasznalo("munkas2", "munkas2", "Szalacsi Sandor", Felhasznalo.FELHASZNALO, true);
    	session.save(u4);
    	
        
        /**
         * Összekapcsolások
         */
    	t1.setFelelos(u0);
    	t1.setMerfoldko(m3);
    	
    	t2.setFelelos(u0);
    	t2.setMerfoldko(m0);
    	
        t3.setFelelos(u3);
        t3.setMerfoldko(m3);
        
        t4.setFelelos(u3);
        t4.setMerfoldko(m3);
        
        t5.setFelelos(u4);
        t5.setMerfoldko(m0);
        
        t6.setFelelos(u4);
        t6.setMerfoldko(m0);
        
        t7.setFelelos(u4);
        t7.setMerfoldko(m1);
        
        t8.setFelelos(u0);
        t8.setMerfoldko(m0);
        
        t9.setFelelos(u0);
        t9.setMerfoldko(m2);
            	
    	
    	session.getTransaction().commit();
	}

	/**
	 * Get requestek lekezelese.
	 */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
     
        PrintWriter out = response.getWriter();

		out.println(HEADER);
		out.println("<h1> 1. feladat </h1>");
		
		if (isEmptyDB()) {
			initializeDB();
			out.println("<p>Kezdetleges adatok beszurva!</p>");
		} else {
			out.println("<p>Nem ures az adatbazis!!!</p>");
		}
		
		out.println(FOOTER);
    }
    
    
	/**
	 * Post requestek lekezelese. Ugyanazt csinaljuk, mint GET eseten.
	 */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

}