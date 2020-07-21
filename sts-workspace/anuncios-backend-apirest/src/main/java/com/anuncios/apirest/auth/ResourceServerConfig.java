package com.anuncios.apirest.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	// Sólo implementamos el método que nos permite implementar las reglas de seguridad de nuestros endpoints
	
	@Override
	public void configure(HttpSecurity http) throws Exception {

		http
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/api/anuncios")  // Rutas 
			.permitAll()  // Permitir a todos
			.anyRequest().authenticated(); // Se coloca al final de todas las rutas que no hayamos asignado permisos
	}	
}
