/**
 * 
 */
package csillag.controller;

import java.util.List;

import org.hibernate.Session;

import csillag.model.Ticket;
import csillag.util.HibernateUtil;

/**
 * @author hargitaidavid
 *
 */
public class TicketController {

	public List getOsszesTicket()
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
		List osszesTicket = session.createQuery("from Ticket").list();
		session.getTransaction().commit();
		
		return osszesTicket;
	}
	
}
