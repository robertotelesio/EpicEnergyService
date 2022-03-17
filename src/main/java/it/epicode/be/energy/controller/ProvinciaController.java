package it.epicode.be.energy.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.epicode.be.energy.model.Provincia;
import it.epicode.be.energy.service.ProvinciaService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class ProvinciaController {

	@Autowired
	ProvinciaService provinciaService;

	@GetMapping(path = "/provincia")
	@Operation(description = "visualizza lista di province")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Provincia>> findAll(Pageable pageable) {
		Page<Provincia> findAll = provinciaService.findAll(pageable);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/provincia/{id}")
	@Operation(description = "cerca provincia per id")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Provincia> findById(@PathVariable(required = true) Long id) {
		Optional<Provincia> find = provinciaService.findById(id);

		if (find.isPresent()) {
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping(path = "/provincia")
	@Operation(description = "crea nuova provincia")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Provincia> save(@RequestBody Provincia provincia) {
		Provincia save = provinciaService.save(provincia);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@PutMapping(path = "/provincia/{id}")
	@Operation(description = "modifica provincia")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Provincia> update(@PathVariable Long id, @RequestBody Provincia provincia) {
		Provincia save = provinciaService.update(id, provincia);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@DeleteMapping(path = "/provincia/{id}")
	@Operation(description = "cancella provincia")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		provinciaService.delete(id);
		return new ResponseEntity<>("Provincia cancellata", HttpStatus.OK);

	}

}
