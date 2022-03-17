package it.epicode.be.energy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.energy.model.StatoFattura;
import it.epicode.be.energy.repository.StatoFatturaRepository;
import it.epicode.be.energy.security.exceptions.EpicEnergyException;

@Service
public class StatoFatturaService {
	
	@Autowired
	StatoFatturaRepository statoFatturaRepo;
	
	public Optional<StatoFattura> findById(Long id) {
		return statoFatturaRepo.findById(id);
	}
	
	
	public StatoFattura save(StatoFattura statoFattura) {
		statoFatturaRepo.save(statoFattura);
		return statoFattura;
	}

	public void delete(Long id) {
		statoFatturaRepo.deleteById(id);
	}

	public StatoFattura update(Long id, StatoFattura statoFattura) {
		Optional<StatoFattura> statoFatturaResult = statoFatturaRepo.findById(id);
		if (statoFatturaResult.isPresent()) {
			StatoFattura update = statoFatturaResult.get();
			update.setNome(statoFattura.getNome());
			
			
			return statoFatturaRepo.save(update);
		}else {
		throw new EpicEnergyException("Stato Fattura non trovato");
		}
	}

	public Page<StatoFattura> findAll(Pageable pageable) {
		return statoFatturaRepo.findAll(pageable);
	}

	public List<StatoFattura> findAll() {
		return statoFatturaRepo.findAll();
	}
	
	

}
