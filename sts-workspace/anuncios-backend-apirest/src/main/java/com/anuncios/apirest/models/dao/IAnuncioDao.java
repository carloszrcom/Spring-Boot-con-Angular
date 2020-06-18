package com.anuncios.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuncios.apirest.models.entity.Anuncio;

public interface IAnuncioDao extends JpaRepository<Anuncio, Long> {  // Antes de la paginación realizábamos el extends de CrudRepository

}
