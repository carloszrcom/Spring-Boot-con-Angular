package com.anuncios.apirest.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.anuncios.apirest.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> { // Como no necesitamos paginar ni ordenar no es necesario extender de JpaRepository

	public Usuario findByUsername(String username);
	
	@Query("select u from usuario u where u.username=?1") // Ejemplo si tuviéramos dos parámetros (en los parámetros del método tendríamos otro, ej.: (String username, String otro)): "select u from usuario u where u.username=?1 and u.otro=?2"
	public Usuario findByUsername2(String username);	
}
