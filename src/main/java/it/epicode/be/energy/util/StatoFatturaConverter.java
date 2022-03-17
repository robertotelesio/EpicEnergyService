package it.epicode.be.energy.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import it.epicode.be.energy.model.StatoFattura;
import it.epicode.be.energy.service.StatoFatturaService;


	@Component
	public class StatoFatturaConverter implements Converter<Long, StatoFattura>{
		
		@Autowired
		StatoFatturaService statoFatturaService;

		@Override
		public StatoFattura convert(Long id) {
			
			return statoFatturaService.findById(id).get();
		}

	}


