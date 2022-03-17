package it.epicode.be.energy.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import it.epicode.be.energy.model.Comune;
import it.epicode.be.energy.service.ComuneService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

@Component
public class ComuneConverter implements Converter<Long, Comune> {
	
	@Autowired
	ComuneService comuneService;

	@Override
	public Comune convert(Long idComune) {
		// TODO Auto-generated method stub
		return comuneService.findById(idComune).get();
	}

	

}
