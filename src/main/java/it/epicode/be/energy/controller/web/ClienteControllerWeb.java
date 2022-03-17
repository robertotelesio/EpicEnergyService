package it.epicode.be.energy.controller.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.epicode.be.energy.model.Cliente;
import it.epicode.be.energy.model.Comune;
import it.epicode.be.energy.service.ClienteService;
import it.epicode.be.energy.service.IndirizzoService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/clienti")
public class ClienteControllerWeb {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	IndirizzoService indirizzoService;
	

	@GetMapping("/mostraformaggiungi")
	public String mostraFormAggiungi(Cliente cliente, Model model) {
		log.info("Form aggiungi cliente");
		model.addAttribute("listaIndirizzi", indirizzoService.findAll());
		return "formCliente";
	}
	
	@GetMapping("/mostraelenco")
	public ModelAndView mostraElencoCliente() {
		log.info("Test elenco clienti su pagina Thymeleaf");
		ModelAndView view = new ModelAndView("elencoclienti");
		view.addObject("listaClienti", clienteService.findAll());
		
		return view;
	}
	

	@PostMapping("/addCliente")
	public String aggiungiCliente(Cliente cliente, BindingResult result, Model model) {
		log.info("Test aggiungi cliente");
		if(result.hasErrors()) {
			model.addAttribute("listaIndirizzi", indirizzoService.findAll());
			return "formCliente";
		}
		clienteService.save(cliente);
		return "redirect:/clienti/mostraelenco";
	}
	
	@GetMapping("/mostraformaggiorna/{id}")
	public ModelAndView mostraFormAggiorna(@PathVariable Long id, Model model) {
		log.info("Form aggiorna cliente");
		Optional<Cliente> cliTemp = clienteService.findById(id);
		if(cliTemp.isPresent()) {
			ModelAndView view = new ModelAndView("editCliente");
			view.addObject("cliente",cliTemp.get());
			view.addObject("listaIndirizzi", indirizzoService.findAll());
			return view;
		}
		return new ModelAndView("error").addObject("message", "id non trovato");
	}
	
	
	@GetMapping("/eliminacliente/{id}")
	public ModelAndView eliminaCliente(@PathVariable Long id, Model model) {
		Optional<Cliente> cliElim = clienteService.findById(id);
		if(cliElim.isPresent()) {
			clienteService.delete(cliElim.get().getId());
			ModelAndView view = new ModelAndView("elencoclienti");
			view.addObject("listaClienti", clienteService.findAll());
			return view;
		}else {
			return new ModelAndView("error").addObject("message", "id non trovato");
		}
	}
	
	@PostMapping("/aggiornacliente/{id}")
	public String aggiornaCliente(@PathVariable Long id, Cliente cliente, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "error";
		}
		clienteService.update(id, cliente);
		log.info("Cliente aggiornato");
		return "redirect:/clienti/mostraelenco";

	}
	

}
