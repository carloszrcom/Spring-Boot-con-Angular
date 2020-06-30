package com.anuncios.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

	@NotNull(message = "No puede estar vacío.")
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;

//	@PrePersist  // Ocurre antes de que se haga un save(un persist). Es un evento del ciclo de vida de las clases entity
//	public void prePersist() {
//		createAt = new Date();
//	}
	
	private String foto;
	
	// El anuncio contiene una región, pero una región puede tener muchos anuncios
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Omite la generación de atributos adicionales en la generación del JSON al utilizar el proxy LAZY. Si no jhacemos esto lanzará un error.
	private Region region;
	
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
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
