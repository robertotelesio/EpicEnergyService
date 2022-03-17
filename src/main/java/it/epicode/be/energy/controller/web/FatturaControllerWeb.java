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

import it.epicode.be.energy.model.Fattura;
import it.epicode.be.energy.model.Indirizzo;
import it.epicode.be.energy.service.ClienteService;
import it.epicode.be.energy.service.FatturaService;
import it.epicode.be.energy.service.StatoFatturaService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/fatture")
public class FatturaControllerWeb {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	FatturaService fatturaService;
	
	@Autowired
	StatoFatturaService statoFatturaService;
	
	

	@GetMapping("/mostraformaggiungi")
	public String mostraFormAggiungi(Fattura fattura, Model model) {
		log.info("Form aggiungi fattura");
		model.addAttribute("listaStatoFatture", statoFatturaService.findAll());
		model.addAttribute("listaClienti", clienteService.findAll());
		return "formFattura";
	}

	@GetMapping("/mostraelenco")
	public ModelAndView mostraElencoFatture() {
		log.info("Test elenco clienti su pagina Thymeleaf");
		ModelAndView view = new ModelAndView("elencofatture");
		view.addObject("listaFatture", fatturaService.findAll());
		
		return view;
	}
	
	@PostMapping("/addFattura")
	public String aggiungiFattura(Fattura fattura, BindingResult result, Model model) {
		log.info("Test aggiungi fattura");
		if(result.hasErrors()) {
			model.addAttribute("listaStatoFatture", statoFatturaService.findAll());
			model.addAttribute("listaClienti", clienteService.findAll());
			return "formFattura";
		}
		fatturaService.save(fattura);
		return "redirect:/fatture/mostraelenco";
	}
	
	@GetMapping("/mostraformaggiorna/{id}")
	public ModelAndView mostraFormAggiorna(@PathVariable Long id, Model model) {
		log.info("Form aggiorna fattura");
		Optional<Fattura> fatTemp = fatturaService.findById(id);
		if(fatTemp.isPresent()) {
			ModelAndView view = new ModelAndView("editFatture");
			view.addObject("listaClienti", clienteService.findAll());
			view.addObject("fattura",fatTemp.get());
			view.addObject("listaStatoFatture", statoFatturaService.findAll());
			return view;
		}
		return new ModelAndView("error").addObject("message", "id non trovato");
	}

	

	@GetMapping("/eliminafattura/{id}")
	public ModelAndView eliminaFattura(@PathVariable Long id, Model model) {
		Optional<Fattura> fatElim = fatturaService.findById(id);
		if(fatElim.isPresent()) {
			fatturaService.delete(fatElim.get().getId());
			ModelAndView view = new ModelAndView("elencofatture");
			view.addObject("listaFatture", fatturaService.findAll());
			return view;
		}else {
			return new ModelAndView("error").addObject("message", "id non trovato");
		}
	
	}

		@PostMapping("/aggiornafattura/{id}")
		public String aggiornaFattura(@PathVariable Long id, Fattura fattura, BindingResult result,
				Model model) {
			if (result.hasErrors()) {
				return "error";
			}
			fatturaService.update(id, fattura);
			log.info("Indirizzo aggiornato");
			return "redirect:/fatture/mostraelenco";

	}
}
