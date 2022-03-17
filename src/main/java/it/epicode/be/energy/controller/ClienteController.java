package it.epicode.be.energy.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import it.epicode.be.energy.service.ClienteService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {

	@Autowired
	ClienteService clienteService;

	@GetMapping(path = "/cliente")
	@Operation(description = "visualizza lista dei clienti")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Cliente>> findAll(Pageable pageable) {
		Page<Cliente> findAll = clienteService.findAll(pageable);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/cliente/{id}")
	@Operation(description = "visualizza cliente dell'id indicato")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Cliente> findById(@PathVariable(required = true) Long id) {
		Optional<Cliente> find = clienteService.findById(id);

		if (find.isPresent()) {
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping(path = "/cliente")
	@Operation(description = "aggiunta di un cliente")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Cliente> save(@RequestBody Cliente cliente) {
		Cliente save = clienteService.save(cliente);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@PutMapping(path = "/cliente/{id}")
	@Operation(description = "modifica di un cliente")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente cliente) {
		Cliente save = clienteService.update(id, cliente);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@DeleteMapping(path = "/cliente/{id}")
	@Operation(description = "eliminazione cliente")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		clienteService.delete(id);
		return new ResponseEntity<>("Cliente eliminato", HttpStatus.OK);

	}

	@GetMapping(path = "/cliente/sortbyragionasociale")
	@Operation(description = "ordina lista clienti per ragione sociale")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Optional<Cliente>>> findByOrderByRagioneSociale(Pageable pageable) {
		Page<Optional<Cliente>> findAll = clienteService.findByOrderByRagioneSociale(pageable);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/cliente/sortbyfatturato")
	@Operation(description = "ordina lista clienti per fatturato annuale")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Optional<Cliente>>> findByOrderByFatturatoAnnuale(Pageable pageable) {
		Page<Optional<Cliente>> findAll = clienteService.findByOrderByFatturatoAnnuale(pageable);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/cliente/sortbyinserimento")
	@Operation(description = "ordina lista clienti per data di inserimento")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Optional<Cliente>>> findByOrderByDataInserimento(Pageable pageable) {
		Page<Optional<Cliente>> findAll = clienteService.findByOrderByDataInserimento(pageable);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/cliente/sortbyultimocontatto")
	@Operation(description = "ordina lista clienti per ultimo contatto")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Optional<Cliente>>> findByOrderByDataUltimoContatto(Pageable pageable) {
		Page<Optional<Cliente>> findAll = clienteService.findByOrderByDataUltimoContatto(pageable);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/cliente/sortbyfatturato/{minimo}/{massimo}")
	@Operation(description = "cerca lista clienti per range di fatturato annuale")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Optional<Cliente>>> findByFatturatoAnnuale(@PathVariable double minimo,
			@PathVariable double massimo, Pageable pageable) {
		Page<Optional<Cliente>> findAll = clienteService.findByFatturatoAnnuale(pageable, minimo, massimo);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/cliente/sortbydatainserimento/{dataInserimento}")
	@Operation(description = "cerca lista clienti per data inserimento")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Optional<Cliente>>> findByDataInserimento(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInserimento, Pageable pageable) {
		Page<Optional<Cliente>> findAll = clienteService.findByDataInserimento(pageable, dataInserimento);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/cliente/sortbyultimocontatto/{dataUltimoContatto}")
	@Operation(description = "cerca lista clienti per data ultimo contatto")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Optional<Cliente>>> findByDataUltimoContatto(
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataUltimoContatto,
			Pageable pageable) {
		Page<Optional<Cliente>> findAll = clienteService.findByDataUltimoContatto(pageable, dataUltimoContatto);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/cliente/sortbyragionesociale/{ragioneSociale}")
	@Operation(description = "cerca lista clienti per ragione sociale")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<Page<Optional<Cliente>>> findByRagioneSociale(
			@PathVariable(required = true) String ragioneSociale, Pageable pageable) {
		Page<Optional<Cliente>> findAll = clienteService.findByRagioneSociale(pageable, ragioneSociale);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

}
