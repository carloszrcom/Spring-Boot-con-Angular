package com.anuncios.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anuncios.apirest.models.dao.IAnuncioDao;
import com.anuncios.apirest.models.entity.Anuncio;
import com.anuncios.apirest.models.entity.Region;

@Service
public class AnuncioServiceImpl implements IAnuncioService {

	@Autowired
	private IAnuncioDao anuncioDao;
	
	@Override
	@Transactional(readOnly = true) // Los métodos de CrudRepository ya incluyen Transactional, con lo que lo podríamos omitir. Con los métodos nuevos sí lo tenemos que usar.
	public List<Anuncio> findAll() {		
		return (List<Anuncio>) anuncioDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Anuncio> findAll(Pageable pageable) {
		return anuncioDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Anuncio findById(Long id) {
		return anuncioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Anuncio save(Anuncio anuncio) {
		return anuncioDao.save(anuncio);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		anuncioDao.deleteById(id);		
	}

	@Override
	@Transactional(readOnly = true)  // Para que lo que se realice sea de sólo lectura
	public List<Region> findAllRegiones() {
		return anuncioDao.findAllRegiones();
	}
}
