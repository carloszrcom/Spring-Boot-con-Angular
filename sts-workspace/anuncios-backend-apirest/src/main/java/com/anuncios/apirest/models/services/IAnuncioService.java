package com.anuncios.apirest.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anuncios.apirest.models.entity.Anuncio;
import com.anuncios.apirest.models.entity.Region;

public interface IAnuncioService {

	public List<Anuncio> findAll();
	
	public Page<Anuncio> findAll(Pageable pageable); // Para paginar los resultados
	
	public Anuncio findById(Long id);
	
	public Anuncio save(Anuncio anuncio);
	
	public void delete(Long id);
	
	public List<Region> findAllRegiones();
}
