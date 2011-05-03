/**
 * 
 */
package csillag.model;

import java.util.*;

/**
 * @author hargitaidavid
 *
 */
public class Ticket {

	/**
	 * Fontossági értékek
	 */
	public static final byte NAGYON_SURGOS = 1;
	public static final byte FONTOS = 3;
	public static final byte NORMAL = 5;
	public static final byte RAER = 7;
	
	/**
	 * Állapotok
	 */
	public static final byte UJ = 1;
	public static final byte MEGOLDVA = 2;
	public static final byte NEM_LESZ_MEGOLDVA = 3;
	public static final byte MODOSITVA = 4;
	public static final byte TOROLVE = 5;
	
	private Long id;
	private String cim;
	private String leiras;
	private byte fontossag;
	private byte allapot;
	private Date letrehozva;
	private Felhasznalo felelos;
	private Merfoldko merfoldko;
	private Set<Csatolmany> csatolmanyok;
	
	public Ticket() {}
	public Ticket(String cim, String leiras, byte fontossag, byte allapot, Date letrehozva) {
		this.cim = cim;
		this.leiras = leiras;
		this.fontossag = fontossag;
		this.allapot = allapot;
		this.letrehozva = letrehozva;
	}
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
	public String getLeiras() {
		return leiras;
	}
	public void setLeiras(String leiras) {
		this.leiras = leiras;
	}
	public byte getFontossag() {
		return fontossag;
	}
	public void setFontossag(byte fontossag) {
		this.fontossag = fontossag;
	}
	public byte getAllapot() {
		return allapot;
	}
	public void setAllapot(byte allapot) {
		this.allapot = allapot;
	}
	public Date getLetrehozva() {
		return letrehozva;
	}
	public void setLetrehozva(Date letrehozva) {
		this.letrehozva = letrehozva;
	}
	public Felhasznalo getFelelos() {
		return felelos;
	}
	public void setFelelos(Felhasznalo felelos) {
		this.felelos = felelos;
	}
	public Merfoldko getMerfoldko() {
		return merfoldko;
	}
	public void setMerfoldko(Merfoldko merfoldko) {
		this.merfoldko = merfoldko;
	}
	public Set<Csatolmany> getCsatolmanyok() {
		return csatolmanyok;
	}
	public void setCsatolmanyok(Set<Csatolmany> csatolmanyok) {
		this.csatolmanyok = csatolmanyok;
	}

}
