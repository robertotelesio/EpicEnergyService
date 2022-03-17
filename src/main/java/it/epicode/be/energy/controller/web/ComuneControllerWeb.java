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
import it.epicode.be.energy.model.Provincia;
import it.epicode.be.energy.service.ComuneService;
import it.epicode.be.energy.service.ProvinciaService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/comuni")
public class ComuneControllerWeb {
	
	@Autowired
	ComuneService comuneService;
	
	@Autowired 
	ProvinciaService provinciaService;
	
	@GetMapping("/mostraelenco")
	public ModelAndView mostraElencoComune() {
		log.info("Test elenco comuni su pagina Thymeleaf");
		ModelAndView view = new ModelAndView("elencocomuni");
		view.addObject("listaComuni", comuneService.findAll());
		
		return view;
	}
	
	@GetMapping("/mostraformaggiungi")
	public String mostraFormAggiungi(Comune comune, Model model) {
		log.info("Form aggiungi comune");
		model.addAttribute("listaProvince", provinciaService.findAll());
		return "formComune";
	}

	@PostMapping("/addComune")
	public String aggiungiComune(Comune comune, BindingResult result, Model model) {
		log.info("Test aggiungi comune");
		if(result.hasErrors()) {
			model.addAttribute("listaProvince", provinciaService.findAll());
			return "formComune";
		}
		comuneService.save(comune);
		return "redirect:/comuni/mostraelenco";
	}
	
	@GetMapping("/mostraformaggiorna/{id}")
	public ModelAndView mostraFormAggiorna(@PathVariable Long id, Model model) {
		log.info("Form aggiorna comune");
		Optional<Comune> comTemp = comuneService.findById(id);
		if(comTemp.isPresent()) {
			ModelAndView view = new ModelAndView("editComune");
			view.addObject("comune",comTemp.get());
			view.addObject("listaProvince", provinciaService.findAll());
			return view;
		}
		return new ModelAndView("error").addObject("message", "id non trovato");
	}
	
	@PostMapping("/aggiornacomune/{id}")
	public String aggiornaComune(@PathVariable Long id, Comune comune, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "error";
		}
		comuneService.update(id, comune);
		log.info("Comune aggiornato");
		return "redirect:/comuni/mostraelenco";

	}
	
	@GetMapping("/eliminacomune/{id}")
	public ModelAndView eliminaComune(@PathVariable Long id, Model model) {
		Optional<Comune> comElim = comuneService.findById(id);
		if(comElim.isPresent()) {
			comuneService.delete(comElim.get().getId());
			ModelAndView view = new ModelAndView("elencocomuni");
			view.addObject("listaComuni", comuneService.findAll());
			return view;
		}else {
			return new ModelAndView("error").addObject("message", "id non trovato");
		}
	}
	

}
