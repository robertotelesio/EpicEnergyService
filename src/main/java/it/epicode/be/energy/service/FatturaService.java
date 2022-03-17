package it.epicode.be.energy.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.energy.model.Cliente;
import it.epicode.be.energy.model.Fattura;
import it.epicode.be.energy.repository.FatturaRepository;
import it.epicode.be.energy.security.exceptions.EpicEnergyException;

@Service
public class FatturaService {
	
	@Autowired
	private FatturaRepository fatturaRepo;

	public Fattura save(Fattura fattura) {
		fatturaRepo.save(fattura);
		return fattura;
	}

	public void delete(Long id) {
		fatturaRepo.deleteById(id);
	}

	public Fattura update(Long id, Fattura fattura) {
		Optional<Fattura> fatturaResult = fatturaRepo.findById(id);
		if (fatturaResult.isPresent()) {
			Fattura update = fatturaResult.get();
			update.setAnno(fattura.getAnno());
			update.setData(fattura.getData());
			update.setImporto(fattura.getImporto());
			update.setNumero(fattura.getNumero());
			update.setStatoFattura(fattura.getStatoFattura());
			
			return fatturaRepo.save(update);
		}else {
		throw new EpicEnergyException("Cliente non trovato");
		}
	}

	public Page<Fattura> findAll(Pageable pageable) {
		return fatturaRepo.findAll(pageable);
	}

	public List<Fattura> findAll() {
		return fatturaRepo.findAll();
	}

	public Optional<Fattura> findById(Long id) {
		return fatturaRepo.findById(id);
	}
	
	public Page<Optional<Fattura>> findByCliente(Pageable pageable, Cliente cliente){
		return fatturaRepo.findByCliente(pageable, cliente);
	}
	
	public Page<Optional<Fattura>> findByStatoFattura(Pageable pageable, String statoFattura){
		return fatturaRepo.findByStatoFattura(pageable, statoFattura);
	}
	
	public Page<Optional<Fattura>> findByData(Pageable pageable, LocalDate data){
		return fatturaRepo.findByData(pageable, data);
	}
	
	public Page<Optional<Fattura>> findByAnno(Pageable pageable, String anno){
		return fatturaRepo.findByAnno(pageable, anno);
	}
	
	public Page<Optional<Fattura>> findByRangeImporto(Pageable pageable, BigDecimal minimo, BigDecimal massimo){
		return fatturaRepo.findByRangeImporto(pageable, minimo, massimo);
	}


}
