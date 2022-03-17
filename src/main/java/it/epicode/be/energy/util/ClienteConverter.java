package it.epicode.be.energy.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import it.epicode.be.energy.model.Cliente;
import it.epicode.be.energy.service.ClienteService;


@Component
public class ClienteConverter implements Converter<Long, Cliente> {
	
	@Autowired
	ClienteService clienteService;

	@Override
	public Cliente convert(Long id) {
		
		return clienteService.findById(id).get();
	}

	

	

}