package com.ejemplo.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplo.entity.Saludo;

@RestController
public class SaludoController {

	private static final String template = "Hola, %s!";
	private final AtomicLong contador = new AtomicLong();
	
	@GetMapping("/saludo")
	public Saludo saludo(@RequestParam(value = "nombre", defaultValue = "mundoooo") String nombre) {
		
		return new Saludo(contador.incrementAndGet(), String.format(template, nombre));
	}
}
