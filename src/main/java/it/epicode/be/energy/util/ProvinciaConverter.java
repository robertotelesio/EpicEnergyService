package it.epicode.be.energy.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import it.epicode.be.energy.model.Provincia;
import it.epicode.be.energy.repository.ProvinciaRepository;
import it.epicode.be.energy.service.ProvinciaService;



@Component
public class ProvinciaConverter implements Converter<Long, Provincia> {
	
	@Autowired
	ProvinciaService provinciaService;
	
	

	@Override
	public Provincia convert(Long id) {
		
		return provinciaService.findById(id).get();
	}
	
	

}
