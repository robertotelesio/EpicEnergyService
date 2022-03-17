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
import it.epicode.be.energy.model.StatoFattura;
import it.epicode.be.energy.service.StatoFatturaService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class StatoFatturaController {

	@Autowired
	StatoFatturaService statoFatturaService;

	@GetMapping(path = "/statofattura")
	@Operation(description = "visualizza lista di stati delle fatture")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<StatoFattura>> findAll(Pageable pageable) {
		Page<StatoFattura> findAll = statoFatturaService.findAll(pageable);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/statofattura/{id}")
	@Operation(description = "cerca stato fattura per id indicato")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<StatoFattura> findById(@PathVariable(required = true) Long id) {
		Optional<StatoFattura> find = statoFatturaService.findById(id);

		if (find.isPresent()) {
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping(path = "/statofattura")
	@Operation(description = "crea nuovo stato fattura")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<StatoFattura> save(@RequestBody StatoFattura statoFattura) {
		StatoFattura save = statoFatturaService.save(statoFattura);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@PutMapping(path = "/statofattura/{id}")
	@Operation(description = "modifica stato fattura")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<StatoFattura> update(@PathVariable Long id, @RequestBody StatoFattura statoFattura) {
		StatoFattura save = statoFatturaService.update(id, statoFattura);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@DeleteMapping(path = "/statofattura/{id}")
	@Operation(description = "elimina stato fattura")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		statoFatturaService.delete(id);
		return new ResponseEntity<>("Provincia cancellata", HttpStatus.OK);

	}

}
