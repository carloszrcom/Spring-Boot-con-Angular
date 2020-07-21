package com.anuncios.apirest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	@Qualifier("authenticationManager") // Así indicamos el nombre del Bean que queremos inyectar, por si tuviéramos más instancias de este tipo
	private AuthenticationManager authenticationManager; // Por defecto no es un bean, hay que registrarlo en el contenedor de Spring

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

		security
			.tokenKeyAccess("permitAll()") // Generar el token. Damos acceso a cualquier usuario, anónimo o no, para poder autenticarse
			.checkTokenAccess("isAuthenticated()");	// Validar el token. Dar permiso al endpoint que se encarga de validar el token. Cada vez que queremos acceder a una página protegida tenemos wque validar nuestro token.
	}

	//////////////////////////////////////
	
	// Aquí se configuran los permisos de nuestros endpoints
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		// Creamos un nuevo cliente
		clients
			.inMemory()
			.withClient("angularapp")  // Cada aplicación tiene sus propias credenciales (angular, react, ...)
			.secret(passwordEncoder.encode("12345")) // La contraseña
			.scopes("read", "write") // El alcance, el permiso q va a tener el cliente (la app)
			.authorizedGrantTypes("password", "refresh_token")  // Tipo de concesión del token. Utilizamos password cuando es usuario + contraseña
			.accessTokenValiditySeconds(3600) // Validez del accesstoken
			.refreshTokenValiditySeconds(3600); // Validez del refreshtoken
	}
	
	//////////////////////////////////////

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
		.authenticationManager(authenticationManager) // Primero registramos el authenticationManager
		.accessTokenConverter(accessTokenConverter()); // Componente que tenemos que registrar; almacena los datos de autenticación; recomendable que no guarde info sensible

	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		return jwtAccessTokenConverter;
	}
}