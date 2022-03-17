package it.epicode.be.energy.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.be.energy.model.Cliente;
import it.epicode.be.energy.model.Fattura;
import it.epicode.be.energy.model.Indirizzo;
import it.epicode.be.energy.repository.ClienteRepository;
import it.epicode.be.energy.repository.FatturaRepository;
import it.epicode.be.energy.repository.IndirizzoRepository;
import it.epicode.be.energy.security.exceptions.EpicEnergyException;



@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private FatturaRepository fatturaRepo; 
	
	@Autowired
	private IndirizzoRepository indirizzoRepo;
	
	

	public Cliente save(Cliente cliente) {
		clienteRepo.save(cliente);
		return cliente;
	}	
	
	public void delete(Long id) {
		
		Cliente delete = clienteRepo.findById(id).get();
	Indirizzo sedeLeg = indirizzoRepo.findById(delete.getIndirizzoSedeLegale().getId()).get();
	Indirizzo sedeOpe = indirizzoRepo.findById(delete.getIndirizzoSedeOperativa().getId()).get();
	sedeLeg.setComune(null);
	sedeOpe.setComune(null);
	indirizzoRepo.delete(sedeLeg);
	indirizzoRepo.delete(sedeOpe);
		List<Fattura> fatture = fatturaRepo.findByClienteId(id);
		for(Fattura f : fatture) {
			fatturaRepo.delete(f);
		}
			
		clienteRepo.deleteById(id);
	}

	public Cliente update(Long id, Cliente cliente) {
		Optional<Cliente> clienteResult = clienteRepo.findById(id);
		if (clienteResult.isPresent()) {
			Cliente update = clienteResult.get();
			update.setRagioneSociale(cliente.getRagioneSociale());
			update.setTipoCliente(cliente.getTipoCliente());
			update.setPartitaIva(cliente.getPartitaIva());
			update.setEmail(cliente.getEmail());
			update.setIndirizzoSedeLegale(cliente.getIndirizzoSedeLegale());
			update.setIndirizzoSedeOperativa(cliente.getIndirizzoSedeOperativa());
			update.setDataInserimento(cliente.getDataInserimento());
			update.setDataUltimoContatto(cliente.getDataUltimoContatto());
			update.setFatturatoAnnuale(cliente.getFatturatoAnnuale());
			update.setPec(cliente.getPec());
			update.setTelefono(cliente.getTelefono());
			update.setCognomeContatto(cliente.getCognomeContatto());
			update.setNomeContatto(cliente.getNomeContatto());
			update.setEmailContatto(cliente.getEmailContatto());
			update.setTelefonoContatto(cliente.getTelefonoContatto());
			update.setFatture(cliente.getFatture());
			
			return clienteRepo.save(update);
		}else {
		throw new EpicEnergyException("Cliente non trovato");
		}
	}

	public Page<Cliente> findAll(Pageable pageable) {
		return clienteRepo.findAll(pageable);
	}

	public List<Cliente> findAll() {
		return clienteRepo.findAll();
	}

	public Optional<Cliente> findById(Long id) {
		return clienteRepo.findById(id);
	}
	
	public Page<Optional<Cliente>> findByOrderByRagioneSociale(Pageable pageable){
		return clienteRepo.findByOrderByRagioneSociale(pageable);
	}
	
	public Page<Optional<Cliente>> findByOrderByFatturatoAnnuale(Pageable pageable){
		return clienteRepo.findByOrderByFatturatoAnnuale(pageable);
	}
	
	public Page<Optional<Cliente>> findByOrderByDataInserimento(Pageable pageable){
		return clienteRepo.findByOrderByDataInserimento(pageable);
	}
	
	public Page<Optional<Cliente>> findByOrderByDataUltimoContatto(Pageable pageable){
		return clienteRepo.findByOrderByDataUltimoContatto(pageable);
	}
	
	public Page<Optional<Cliente>> findByOrderByIndirizzoSedeLegaleComuneProvincia(Pageable pageable){
		return clienteRepo.findByOrderByIndirizzoSedeLegaleComuneProvincia(pageable);
	}
	public Page<Optional<Cliente>> findByFatturatoAnnuale(Pageable pageable, double minimo, double massimo){
		return clienteRepo.findByFatturatoAnnuale(pageable, minimo, massimo);
	}
	
	public Page<Optional<Cliente>> findByDataInserimento(Pageable pageable, LocalDate dataInserimento){
		return clienteRepo.findByDataInserimento(pageable, dataInserimento);
	}
	
	public Page<Optional<Cliente>> findByDataUltimoContatto(Pageable pageable, LocalDate dataUltimoContatto){
		return clienteRepo.findByDataUltimoContatto(pageable, dataUltimoContatto);
	}
	
	public Page<Optional<Cliente>> findByRagioneSociale(Pageable pageable, String regionaSociale){
		return clienteRepo.findByRagioneSociale(pageable, regionaSociale);
	}

}
