package it.epicode.be.energy;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Date;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.epicode.be.energy.model.Cliente;
import it.epicode.be.energy.model.Comune;
import it.epicode.be.energy.model.Indirizzo;
import it.epicode.be.energy.model.Provincia;
import it.epicode.be.energy.model.TipoCliente;







@SpringBootTest
@AutoConfigureMockMvc
public class EpicEnergyControllerTest {
	
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	@WithAnonymousUser
	public void listaClientiWhenUtenteIsAnonymous() throws Exception {
		this.mockMvc.perform(get("/api/cliente")).andExpect(status().is3xxRedirection());
	}

	@Test
	@WithAnonymousUser
	public void listaComuniWhenUtenteIsAnonymous() throws Exception {
		this.mockMvc.perform(get("/api/comune")).andExpect(status().is3xxRedirection());
	}

	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	public void listaComuniWhenUtenteMockIsAuthenticated() throws Exception {
		this.mockMvc.perform(get("/api/comune")).andExpect(status().isOk());
	}
	
	@Test
	public void addNewProvinciaWhenUtenteIsAnonymous() throws Exception {
		Provincia provincia = new Provincia();
		provincia.setId(1L);
		provincia.setNome("cosenza");
		provincia.setRegione("Calabria");
		provincia.setSigla("CS");
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(provincia);
		MvcResult result = mockMvc.perform(post("/api/provincia").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().is3xxRedirection()).andReturn();

	}
	
	
	
	
     
     
     
     


}
