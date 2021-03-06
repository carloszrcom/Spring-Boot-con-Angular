package com.anuncios.apirest.controllers;

import java.io.IOException;
import java.net.MalformedURLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anuncios.apirest.models.entity.Anuncio;
import com.anuncios.apirest.models.entity.Region;
import com.anuncios.apirest.models.services.IAnuncioService;
import com.anuncios.apirest.models.services.IUploadFileService;

// Este va a ser nuestro API REST

@CrossOrigin(origins = {"http://localhost:4200"}) // Aquí deberemos establecer restricciones sobre qué métodos se pueden utilizar mediante el parámetro 'methods'
@RestController
@RequestMapping("/api")
public class AnuncioRestController {
	
	@Autowired
	private IUploadFileService uploadService;

	@Autowired
	private IAnuncioService anuncioService; // Cuando se declara un Bean cion su tipo genérico ya sea interfaz o clase abstracta, va a buscar
											// el primer candidato. Una clase concreta que implemente esta interface y la va a inyectar.
											// Si tuviera más de una hay que usar un cualificador (qualifiying)
	
	
	@GetMapping("/anuncios")
	public List<Anuncio> index() {
		return anuncioService.findAll();
	}

	@GetMapping("/anuncios/page/{page}")
	public Page<Anuncio> index(@PathVariable Integer page) {
		return anuncioService.findAll(PageRequest.of(page, 4));
	}
	
	// Muestra un anuncio concreto
	@GetMapping("/anuncios/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) { // Puede ser cualquier tipo de objeto "?"
		
		Map<String, Object> response = new HashMap<String, Object>();
		Anuncio anuncio = null;
		
		try {
			anuncio = anuncioService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMessage().concat(":  ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(anuncio == null) {
			response.put("mensaje", "El anuncio ID: ".concat(id.toString().concat(" no existe en la base de datos.")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Anuncio>(anuncio, HttpStatus.OK);
	}
	
	// Crea un anuncio
	@PostMapping("/anuncios")
	public ResponseEntity<?> create(@Valid @RequestBody Anuncio anuncio, BindingResult result) { // @Valid: Intercepta el objeto 'anuncio' y valida cada valor, cada atributo desde el RequestBody que está enviando Angular en una estructura JSON.

		// anuncio.setCreateAt(new Date());  // La fecha la vamos a indicar en el modelo(entidad)
		Anuncio anuncioNew = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		if (result.hasErrors()) {
			
			/* Forma anterior al JDK 8
			// *** INICIO Esto se puede realizar usando el API Stream de Java, de forma más limpia
			List<String> errors = new ArrayList<>();
			
			for (FieldError err: result.getFieldErrors()) {
				errors.add("El campo: '" + err.getField() + "' " + err.getDefaultMessage());
			}
			// FIN *** 
			*/
			
			List<String> errors = result.getFieldErrors()
					.stream()  // Lo convertimos en un flujo (stream)
					.map(err -> "El campo: '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList()); // Lambda
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			anuncioNew = anuncioService.save(anuncio);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos.");
			response.put("error", e.getMessage().concat(":  ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Anuncio creado con éxito.");
		response.put("anuncio", anuncioNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); 
	}
	
	// Actualizar un anuncio
	@PutMapping("/anuncios/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Anuncio anuncio, BindingResult result , @PathVariable Long id) {
		
		Anuncio anuncioActual = anuncioService.findById(id);
		Anuncio anuncioUpdated = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		if (result.hasErrors()) {
			
			List<String> errors = result.getFieldErrors()
					.stream()  // Lo convertimos en un flujo (stream)
					.map(err -> "El campo: '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList()); // Lambda
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(anuncioActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el cliente ID: ".concat(id.toString().concat(" no existe en la base de datos.")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			anuncioActual.setTitulo(anuncio.getTitulo());
			anuncioActual.setHistoria(anuncio.getHistoria());
			anuncioActual.setCreateAt(anuncio.getCreateAt());
			anuncioActual.setRegion(anuncio.getRegion());
			
			anuncioUpdated = anuncioService.save(anuncioActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el anuncio en la base de datos.");
			response.put("error", e.getMessage().concat(":  ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Anuncio actualizado con éxito.");
		response.put("anuncio", anuncioUpdated);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/anuncios/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			Anuncio anuncio = anuncioService.findById(id);
			String nombreFotoAnterior = anuncio.getFoto();
			
			uploadService.eliminiar(nombreFotoAnterior);
			
			anuncioService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el anuncio en la base de datos.");
			response.put("error", e.getMessage().concat(":  ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Anuncio eliminado con éxito.");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	// Método para subida de imágenes
	@PostMapping("/anuncios/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		Anuncio anuncio = anuncioService.findById(id);
		
		if(!archivo.isEmpty()) {
			
			String nombreArchivo = null;
			
			try {
				nombreArchivo = uploadService.copiar(archivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen del anuncio.");
				response.put("error", e.getMessage().concat(":  ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			String nombreFotoAnterior = anuncio.getFoto();
			
			uploadService.eliminiar(nombreFotoAnterior);
			
			anuncio.setFoto(nombreArchivo);
			anuncioService.save(anuncio);
			
			response.put("anuncio", anuncio);
			response.put("mensaje", "Ha subido correctamente la imagen: " + nombreArchivo);
			
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	// Ver foto
	@GetMapping("/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {
		
		Resource recurso = null;
	
		try {
			recurso = uploadService.cargar(nombreFoto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		// Para forzar la descarga
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() +  "\"");
		
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}
	
	// Listar regiones
	@GetMapping("/anuncios/regiones")
	public List<Region> listarRegiones() {
		return anuncioService.findAllRegiones();
	}
}