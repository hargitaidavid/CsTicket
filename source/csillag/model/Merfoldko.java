package csillag.model;

import java.util.Date;
import java.util.Set;

/**
 * Egy mérföldkövet reprezentáló osztály.
 * 
 * @author Bognár Szabolcs, Hargitai Dávid
 *
 */
public class Merfoldko {

	private Long id;
	private String nev;
	private Date hatarido;
	private Set<Ticket> ticketek;
	
	public Merfoldko() {}
	public Merfoldko(String nev, Date hatarido) {
		this.nev = nev;
		this.hatarido = hatarido;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNev() {
		return nev;
	}
	public void setNev(String nev) {
		this.nev = nev;
	}
	public Date getHatarido() {
		return hatarido;
	}
	public void setHatarido(Date hatarido) {
		this.hatarido = hatarido;
	}
	public Set<Ticket> getTicketek() {
		return ticketek;
	}
	public void setTicketek(Set<Ticket> ticketek) {
		this.ticketek = ticketek;
	}
	
}
