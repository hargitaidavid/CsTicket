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
 * Minta servlet az elso feladat megoldasahoz.
 * A feladat, hogy kezdeti adatokkal feltoltsuk a servlet segitsegevel az adatbazist.
 * Elotte viszont ellenoriznunk kell, hogy vannak-e mar benne adatok.
 *  
 * @author hXXXXXX
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
	 * A feladat minden entitasbol 2-3 peldat letrehozni az esetleges koztuk levo kapcsolattal egyutt.
	 */
    public void initializeDB() {
    	// TODO: megoldani
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
    	Ticket t1;
    	t1 = new Ticket("Fooldali hiba", "Hiba van a fooldalon, meg kene javitani", Ticket.NORMAL, Ticket.UJ, new Date());
    	session.save(t1);
    	
    	t1 = new Ticket("Elromlott a gaztuzhely", "A gaztuzhely surgos javitasra szorul", Ticket.FONTOS, Ticket.UJ, new Date());
    	session.save(t1);
    	
    	t1 = new Ticket("Oldal hatterszin", "Nem szep a bejelentkezo oldal szine", Ticket.RAER, Ticket.UJ, new Date());
    	session.save(t1);
    	
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