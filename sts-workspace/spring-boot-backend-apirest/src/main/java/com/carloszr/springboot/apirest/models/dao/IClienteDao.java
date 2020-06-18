package com.carloszr.springboot.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.carloszr.springboot.apirest.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long> {

	// Para los métodos nuevos que queramos implementar aquí
	// debemos usar la anotación @Transactional
}
