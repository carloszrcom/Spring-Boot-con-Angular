package com.anuncios.apirest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
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

	@Bean("authenticationManager")  // Así ya lo tenemos registrado en el contenedor de Spring; en este casso hemos indicado el nombre del bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	} 

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http
			.authorizeRequests()
			.anyRequest().authenticated() // Cualquier petición requiere autenticación
			.and()
			.csrf().disable()  // No necesitamos esta protección por estar trabajando con Angular
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Deshabilitamos el manejo de sesión en la autenticación por el lado de Spring Security, ya que vamos a trabajar con token
	}
}
