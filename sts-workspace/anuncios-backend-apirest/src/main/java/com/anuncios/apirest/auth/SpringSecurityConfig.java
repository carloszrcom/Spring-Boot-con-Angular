package com.anuncios.apirest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	/*
	 * Al solo haber una implementación de esta interfaz (en UsuarioService) y esta 
	 * estar anotada con @Service (estando así dentro del contenedor), va a inyectar la única implementación
	 * */
	@Autowired
	private UserDetailsService usuarioService;
	
	@Bean // Para registrar en el contenedor de Spring el objeto que retorna este método. Lo llamaremos más tarde mediante Autowired
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// Lo siguiente es registrar en el authentication manager de Spring Security este servicio para autenticar
	// Sobreescribimos un método (clic derecho -> Source -> Override Method)
	@Override
	@Autowired  // Para registrar AuthenticationManagerBuilder
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(this.usuarioService).passwordEncoder(passwordEncoder());
	} 

	
}
