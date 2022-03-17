package it.epicode.be.energy.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.epicode.be.energy.model.Cliente;
import it.epicode.be.energy.model.Fattura;


public interface FatturaRepository extends JpaRepository<Fattura, Long> {
	
	Page<Optional<Fattura>> findByCliente(Pageable pageable, Cliente cliente);
	
	List<Fattura> findByClienteId(Long id);
	
	Page<Optional<Fattura>> findByStatoFattura(Pageable pageable, String statoFattura);
	
	Page<Optional<Fattura>> findByData(Pageable pageable, LocalDate data);
	
	Page<Optional<Fattura>> findByAnno(Pageable pageable, String anno);
	
	@Query("SELECT f FROM Fattura f WHERE f.importo>=:minimo AND f.importo<=:massimo")
	Page<Optional<Fattura>> findByRangeImporto(Pageable pageable, BigDecimal minimo, BigDecimal massimo);
	
	
	
	

}
