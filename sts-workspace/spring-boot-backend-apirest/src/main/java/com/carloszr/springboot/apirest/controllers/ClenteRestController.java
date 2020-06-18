package com.carloszr.springboot.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.carloszr.springboot.apirest.models.entity.Cliente;
import com.carloszr.springboot.apirest.models.services.IClienteService;

@CrossOrigin(origins = {"http://localhost:4200"})   // Para conectar dos aplicaciones (CORS). Poniendo una coma sale la opción de 'methods' para seleccionar los métodos que queremos permitir (GET, PUT, ...).
@RestController
@RequestMapping("/api")
public class ClenteRestController {

	@Autowired
	private IClienteService clienteService; // Si hubiera más de una implementación habría que esar un qualificador
	
	// Listar todos los Clientes
	@GetMapping("/clientes")
	public List<Cliente> index() {
		return clienteService.findAll();
	}
	
	// Encontrar Cliente por id
	@GetMapping("/clientes/{id}")
	@ResponseStatus(value = HttpStatus.OK) // Esto es redundante porque por defecto es OK, nos podíamos ahorrar esta anotación
	public Cliente show(@PathVariable Long id) {
		return clienteService.findById(id);
	}
	
	// Crear
	@PostMapping("/clientes")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Cliente crear(@RequestBody Cliente cliente) {
//		cliente.setCreateAt(new Date()); // Vamos a establecer la fecha en el modelo, en un prePersist
		return clienteService.save(cliente);
	}
	
	// Actualizar los datos
	@PutMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id) {
		// Primero recuperamos el cliente
		Cliente clienteActual = clienteService.findById(id);
		
		clienteActual.setApellido(cliente.getApellido());
		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setEmail(cliente.getEmail());
		
		return clienteService.save(clienteActual);
	}
	
	// Borrar
	@DeleteMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		clienteService.delete(id);
	}
}
