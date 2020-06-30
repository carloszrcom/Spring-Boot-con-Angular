package com.anuncios.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.anuncios.apirest.models.entity.Anuncio;
import com.anuncios.apirest.models.entity.Region;

public interface IAnuncioDao extends JpaRepository<Anuncio, Long> {  // Antes de la paginación realizábamos el extends de CrudRepository

	@Query("from Region")
	public List<Region> findAllRegiones();
}
