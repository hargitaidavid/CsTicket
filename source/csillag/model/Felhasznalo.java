package csillag.model;

import java.util.Set;

/**
 * Egy felhaszn‡l—t reprezent‡l— oszt‡ly.
 * 
 * @author Bogn‡r Szabolcs, Hargitai D‡vid
 *
 */
public class Felhasznalo {

	public static final byte ADMIN = 5;
	public static final byte MODERATOR = 3;
	public static final byte FELHASZNALO = 1;
	
	private Long id;
	private String felhasznalonev;
	private String jelszo;
	private String nev;
	private byte jogkor;
	private boolean dolgozo;
	private Set<Ticket> ticketek;
	
	public Felhasznalo() {}
    public Felhasznalo(String felhasznalonev, String jelszo, String nev, byte jogkor, boolean dolgozo) {
    	this.felhasznalonev = felhasznalonev;
    	this.jelszo = jelszo;
    	this.nev = nev;
    	this.jogkor = jogkor;
    	this.dolgozo = dolgozo;
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFelhasznalonev() {
		return felhasznalonev;
	}
	public void setFelhasznalonev(String felhasznalonev) {
		this.felhasznalonev = felhasznalonev;
	}
	public String getJelszo() {
		return jelszo;
	}
	public void setJelszo(String jelszo) {
		this.jelszo = jelszo;
	}
	public String getNev() {
		return nev;
	}
	public void setNev(String nev) {
		this.nev = nev;
	}
	public byte getJogkor() {
		return jogkor;
	}
	public void setJogkor(byte jogkor) {
		this.jogkor = jogkor;
	}
	public boolean isDolgozo() {
		return dolgozo;
	}
	public void setDolgozo(boolean dolgozo) {
		this.dolgozo = dolgozo;
	}
	public Set<Ticket> getTicketek() {
		return ticketek;
	}
	public void setTicketek(Set<Ticket> ticketek) {
		this.ticketek = ticketek;
	}
    
	
}
