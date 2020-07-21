package com.anuncios.apirest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AnunciosBackendApirestApplication implements CommandLineRunner { // Lo implementamos para generar las contraseñas con el método run

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(AnunciosBackendApirestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		String password = "12345";
		
		// Generamos cuatro contraseñas
		for (int i = 0; i < 4; i++) {
			String passwordBcrypt = passwordEncoder.encode(password);
			System.out.println(passwordBcrypt);
		}
		
		/*
		 * Claves generadas por passwordEncoder:
		 * 
		 * $2a$10$dnMX4L8HCKNK2WhojrRVJurE/XfHDakqLdPfH2A0Z6eDLJ95iaI8G
		 * $2a$10$PfjC2J8mrOKHQraydu.k..LKDpOkk27FyZ4KuHxF9jPmH2hDhn4Pu
		 * $2a$10$uNZHZFKiQFcnyISq83g4s.VYYnRelBdEl4I/5qbgHy9FJ6JYwcmWW
		 * $2a$10$etOteKn2b4AF8lbEnPCb9e5mMnJwj5ywZWZbWkXjAoXJ1/sGzkpUW
		 * */
	}
}
