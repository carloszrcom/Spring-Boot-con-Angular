package com.anuncios.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import net.bytebuddy.implementation.auxiliary.AuxiliaryType.SignatureRelevant;

@Entity
@Table(name="anuncios")
public class Anuncio implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // SEQUENCE es habitual cuando trabajamos con Oracle o Postgres, pero da error con el INSERT al estar null el campo
	private Long id;
	
	@NotEmpty // Con la propiedad 'message' podemos customizar los mensajes
	@Size(min = 4, max = 12)
	@Column(name = "titulo", nullable = false)	
	private String titulo;
	
	@Column(name = "historia")
	private String historia;

	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;

	@PrePersist  // Ocurre antes de que se haga un save(un persist). Es un evento del ciclo de vida de las clases entity
	public void prePersist() {
		createAt = new Date();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	public String getHistoria() {
		return historia;
	}

	public void setHistoria(String historia) {
		this.historia = historia;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}