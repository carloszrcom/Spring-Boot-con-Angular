package com.anuncios.apirest.models.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anuncios.apirest.models.dao.IUsuarioDao;
import com.anuncios.apirest.models.entity.Usuario;

@Service
public class UsuarioService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = usuarioDao.findByUsername(username);
		
		if (usuario == null) {
			logger.error("Error en el login: el usuario '"+ username +"' no existe en el sistema");
			throw new UsernameNotFoundException("Error en el login: el usuario '"+ username +"' no existe en el sistema");
		}
		
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream() // Vamos a convertir cada elemento de esta lista (de este flujo)
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> logger.info("Role: " + authority.getAuthority())) // Va a ir mostrando el nombre del rol en el log
				.collect(Collectors.toList()); // Para convertir a una lista
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}
}
