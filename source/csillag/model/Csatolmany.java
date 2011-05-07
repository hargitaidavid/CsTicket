package csillag.model;

import java.sql.Blob;


/**
 * Egy ticketekhez kapcsolhat— csatolm‡nyf‡jlt reprezent‡l— oszt‡ly..
 * 
 * @author Bogn‡r Szabolcs, Hargitai D‡vid
 *
 */
public class Csatolmany {

	private Long id;
	private String cim;
	private Blob adat;
	private Ticket tartalmazo;
	


	public Csatolmany() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCim() {
		return cim;
	}

	public void setCim(String cim) {
		this.cim = cim;
	}

	public Blob getAdat() {
		return adat;
	}

	public void setAdat(Blob adat) {
		this.adat = adat;
	}

	public Ticket getTartalmazo() {
		return tartalmazo;
	}

	public void setTartalmazo(Ticket tartalmazo) {
		this.tartalmazo = tartalmazo;
	}
	
}
