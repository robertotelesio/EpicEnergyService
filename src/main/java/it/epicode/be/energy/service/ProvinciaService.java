package it.epicode.be.energy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import it.epicode.be.energy.model.Provincia;
import it.epicode.be.energy.repository.ProvinciaRepository;
import it.epicode.be.energy.security.exceptions.EpicEnergyException;


@Service
public class ProvinciaService {
	
	@Autowired
	ProvinciaRepository provinciaRepo;
	
	public Optional<Provincia> findById(Long id) {
		return provinciaRepo.findById(id);
	}
	

	
	
	public Provincia findByNome(String nome) {
		return provinciaRepo.findByNome(nome);
	}
	
	public Provincia save(Provincia provincia) {
		provinciaRepo.save(provincia);
		return provincia;
	}

	public void delete(Long id) {
		provinciaRepo.deleteById(id);
	}

	public Provincia update(Long id, Provincia provincia) {
		Optional<Provincia> provinciaResult = provinciaRepo.findById(id);
		if (provinciaResult.isPresent()) {
			Provincia update = provinciaResult.get();
			update.setNome(provincia.getNome());
			update.setSigla(provincia.getSigla());
			update.setRegione(provincia.getRegione());
			
			return provinciaRepo.save(update);
		}else {
		throw new EpicEnergyException("Provincia non trovata");
		}
	}

	public Page<Provincia> findAll(Pageable pageable) {
		return provinciaRepo.findAll(pageable);
	}

	public List<Provincia> findAll() {
		return provinciaRepo.findAll();
	}


	

}
