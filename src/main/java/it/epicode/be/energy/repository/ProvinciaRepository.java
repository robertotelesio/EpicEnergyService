package it.epicode.be.energy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import it.epicode.be.energy.model.Provincia;

public interface ProvinciaRepository extends JpaRepository<Provincia, Long>{
	
	

	Provincia findByNome(String nome);



}
