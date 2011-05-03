/**
 * 
 */
package csillag.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import org.hibernate.Hibernate;

/**
 * @author hargitaidavid
 *
 */
public class Csatolmany {

	private Long id;
	private String cim;
	private byte[] adat;
	
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

	public byte[] getAdat() {
		return adat;
	}

	public void setAdat(byte[] adat) {
		this.adat = adat;
	}

	
	
}
