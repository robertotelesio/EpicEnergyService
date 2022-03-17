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

import it.epicode.be.energy.model.Indirizzo;
import it.epicode.be.energy.service.ComuneService;
import it.epicode.be.energy.service.IndirizzoService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/indirizzi")
public class IndirizzoControllerWeb {
	
	@Autowired
	IndirizzoService indirizzoService;
	
	@Autowired
	ComuneService comuneService;
	
	@GetMapping("/mostraformaggiungi")
	public String mostraFormAggiungi(Indirizzo indirizzo, Model model) {
		log.info("Form aggiungi indirizzo");
		model.addAttribute("listaComuni", comuneService.findAll());
		return "formIndirizzo";
	}
	
	@GetMapping("/mostraelenco")
	public ModelAndView mostraElencoIndirizzi() {
		log.info("Test elenco clienti su pagina Thymeleaf");
		ModelAndView view = new ModelAndView("elencoindirizzi");
		view.addObject("listaIndirizzi", indirizzoService.findAll());
		return view;
	}
	
	
	@PostMapping("/addIndirizzo")
	public String aggiungiIndirizzo(Indirizzo indirizzo, BindingResult result, Model model) {
		log.info("Test aggiungi cliente");
		if(result.hasErrors()) {
			model.addAttribute("listaComuni", comuneService.findAll());
			return "formIndirizzo";
		}
		indirizzoService.save(indirizzo);
		return "redirect:/indirizzi/mostraelenco";
	}
	
	@GetMapping("/mostraformaggiorna/{id}")
	public ModelAndView mostraFormAggiorna(@PathVariable Long id, Model model) {
		log.info("Form aggiorna indirizzo");
		Optional<Indirizzo> indTemp = indirizzoService.findById(id);
		if(indTemp.isPresent()) {
			ModelAndView view = new ModelAndView("editIndirizzo");
			view.addObject("indirizzo",indTemp.get());
			view.addObject("listaComuni", comuneService.findAll());
			return view;
		}
		return new ModelAndView("error").addObject("message", "id non trovato");
	}

	@PostMapping("/aggiornaindirizzo/{id}")
	public String aggiornaProvincia(@PathVariable Long id, Indirizzo indirizzo, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "error";
		}
		indirizzoService.update(id, indirizzo);
		log.info("Indirizzo aggiornato");
		return "redirect:/indirizzi/mostraelenco";

	}

	@GetMapping("/eliminaindirizzo/{id}")
	public ModelAndView eliminaProvincia(@PathVariable Long id, Model model) {
		Optional<Indirizzo> indElim = indirizzoService.findById(id);
		if(indElim.isPresent()) {
			indirizzoService.delete(indElim.get().getId());
			ModelAndView view = new ModelAndView("elencoindirizzi");
			view.addObject("listaIndirizzi", indirizzoService.findAll());
			return view;
		}else {
			return new ModelAndView("error").addObject("message", "id non trovato");
		}
	}
	

}
