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
import it.epicode.be.energy.model.Provincia;
import it.epicode.be.energy.service.ProvinciaService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/province")
public class ProvinciaControllerWeb {
	
	@Autowired
	ProvinciaService provinciaService;
	
	@GetMapping("/mostraelenco")
	public ModelAndView mostraElencoProvincia() {
		log.info("Test elenco province su pagina Thymeleaf");
		ModelAndView view = new ModelAndView("elencoprovince");
		view.addObject("listaProvince", provinciaService.findAll());
		//model.addAttribute("listaStudenti", studenteService.findAll());
		return view;
	}
	
	@GetMapping("/mostraformaggiungi")
	public String mostraFormAggiungi(Provincia provincia) {
		log.info("Form aggiungi provincia");
		return "formProvincia";
	}

	@PostMapping("/addProvincia")
	public String aggiungiStudente(Provincia provincia, BindingResult result, Model model) {
		log.info("Test aggiungi cliente");
		if(result.hasErrors()) {
			return "formProvincia";
		}
		provinciaService.save(provincia);
		return "redirect:/province/mostraelenco";
	}
	
	@GetMapping("/mostraformaggiorna/{id}")
	public ModelAndView mostraFormAggiorna(@PathVariable Long id, Model model) {
		log.info("Form aggiorna provincia");
		Optional<Provincia> provTemp = provinciaService.findById(id);
		if(provTemp.isPresent()) {
			ModelAndView view = new ModelAndView("editProvincia");
			view.addObject("provincia",provTemp.get());
			
			return view;
		}
		return new ModelAndView("error").addObject("message", "id non trovato");
	}

	@PostMapping("/aggiornaprovincia/{id}")
	public String aggiornaProvincia(@PathVariable Long id, Provincia provincia, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "error";
		}
		provinciaService.update(id, provincia);
		log.info("Provincia aggiornata");
		return "redirect:/province/mostraelenco";

	}

	@GetMapping("/eliminaprovincia/{id}")
	public ModelAndView eliminaProvincia(@PathVariable Long id, Model model) {
		Optional<Provincia> provElim = provinciaService.findById(id);
		if(provElim.isPresent()) {
			provinciaService.delete(provElim.get().getId());
			ModelAndView view = new ModelAndView("elencoprovince");
			view.addObject("listaProvince", provinciaService.findAll());
			return view;
		}else {
			return new ModelAndView("error").addObject("message", "id non trovato");
		}
	}

}
