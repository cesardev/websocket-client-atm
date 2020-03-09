package com.neo.mx.atm.models;

import java.io.Serializable;

public class AtmVO implements Serializable {

	private String titulo;
	private String usuario;
	private Long codigoAtm;
	private String estatus;
	private String motivo;
	private String falla;
	private String fecha;
	
	public AtmVO() {
		super();
	}

	public AtmVO(String titulo, String usuario, Long codigoAtm, String estatus, String motivo, String falla,
			String fecha) {
		super();
		this.titulo = titulo;
		this.usuario = usuario;
		this.codigoAtm = codigoAtm;
		this.estatus = estatus;
		this.motivo = motivo;
		this.falla = falla;
		this.fecha = fecha;
	}


	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Long getCodigoAtm() {
		return codigoAtm;
	}

	public void setCodigoAtm(Long codigoAtm) {
		this.codigoAtm = codigoAtm;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getFalla() {
		return falla;
	}

	public void setFalla(String falla) {
		this.falla = falla;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = -5774469145336708682L;

}
