package it.epicode.be.energy.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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
import it.epicode.be.energy.model.Cliente;
import it.epicode.be.energy.model.Fattura;
import it.epicode.be.energy.model.StatoFattura;
import it.epicode.be.energy.service.FatturaService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class FatturaController {

	@Autowired
	FatturaService fatturaService;

	@GetMapping(path = "/fattura")
	@Operation(description = "visualizza lista fatture")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Fattura>> findAll(Pageable pageable) {
		Page<Fattura> findAll = fatturaService.findAll(pageable);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/fattura/{id}")
	@Operation(description = "cerca fattura per id indicato")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Fattura> findById(@PathVariable(required = true) Long id) {
		Optional<Fattura> find = fatturaService.findById(id);

		if (find.isPresent()) {
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping(path = "/fattura")
	@Operation(description = "crea nuova fattura")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Fattura> save(@RequestBody Fattura fattura) {
		Fattura save = fatturaService.save(fattura);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@PutMapping(path = "/fattura/{id}")
	@Operation(description = "modifica fattura")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Fattura> update(@PathVariable Long id, @RequestBody Fattura fattura) {
		Fattura save = fatturaService.update(id, fattura);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@DeleteMapping(path = "/fattura/{id}")
	@Operation(description = "elimina fattura")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		fatturaService.delete(id);
		return new ResponseEntity<>("Fattura cancellata", HttpStatus.OK);

	}

	@GetMapping(path = "/fattura/sortbycliente/{cliente}")
	@Operation(description = "cerca lista fatture per cliente")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Optional<Fattura>>> findByCliente(Pageable pageable, @PathVariable Cliente cliente) {
		Page<Optional<Fattura>> findAll = fatturaService.findByCliente(pageable, cliente);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/fattura/sortbyimporto/{minimo}/{massimo}")
	@Operation(description = "cerca lista fatture per range di importo")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Optional<Fattura>>> findByRangeImporto(Pageable pageable,
			@PathVariable BigDecimal minimo, @PathVariable BigDecimal massimo) {
		Page<Optional<Fattura>> findAll = fatturaService.findByRangeImporto(pageable, minimo, massimo);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/fattura/sortbystato/{statoFattura}")
	@Operation(description = "cerca lista fatture per stato")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Optional<Fattura>>> findByStatoFattura(Pageable pageable,
			@PathVariable String statoFattura) {
		Page<Optional<Fattura>> findAll = fatturaService.findByStatoFattura(pageable, statoFattura);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/fattura/sortbydata/{data}")
	@Operation(description = "cerca lista fatture per data")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Optional<Fattura>>> findByData(Pageable pageable,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
		Page<Optional<Fattura>> findAll = fatturaService.findByData(pageable, data);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/fattura/sortbyanno/{anno}")
	@Operation(description = "cerca lista fatture per anno")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Optional<Fattura>>> findByAnno(Pageable pageable, @PathVariable String anno) {
		Page<Optional<Fattura>> findAll = fatturaService.findByAnno(pageable, anno);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

}
