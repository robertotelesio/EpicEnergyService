package it.epicode.be.energy.runner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import it.epicode.be.energy.model.Comune;
import it.epicode.be.energy.model.Provincia;
import it.epicode.be.energy.model.StatoFattura;
import it.epicode.be.energy.repository.ComuneRepository;
import it.epicode.be.energy.repository.ProvinciaRepository;
import it.epicode.be.energy.repository.StatoFatturaRepository;
import it.epicode.be.energy.service.ProvinciaService;



@Component
public class ApplicationRunner implements CommandLineRunner {
	
	@Autowired
	ComuneRepository comuneRepo;
	
	@Autowired
	ProvinciaRepository provinciaRepo;
	
	@Autowired
	StatoFatturaRepository statoFatturaRepository;
	

	
	
	

	@Override
	public void run(String... args) throws Exception {
		caricaProvince();
		
		caricaStatoFattura();
				
		caricaComuni();
		
			
		}

	
	
	
	public void caricaProvince() {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
        try (CSVReader reader = new CSVReaderBuilder(
                  new FileReader("provinceitaliane.csv"))
                  .withCSVParser(csvParser)
                  .withSkipLines(1)
                  .build()) {
           String[] values = null;

            while ((values = reader.readNext()) != null) {
            	Provincia prov = new Provincia();
            	prov.setSigla(values[0]);
            	prov.setNome(values[1]);
            	prov.setRegione(values[2]);
            	provinciaRepo.save(prov);
            	
                //provinciaRepo.save(new Provincia(values[0], values[1], values[2]));
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
	
	
	public void caricaStatoFattura() {
		StatoFattura stato = new StatoFattura();
		stato.setNome("Ricevuta");
		
		StatoFattura stato1 = new StatoFattura();
		stato1.setNome("Pagata");
		
		StatoFattura stato2 = new StatoFattura();
		stato2.setNome("Non accettata");
		
		StatoFattura stato3 = new StatoFattura();
		stato3.setNome("Bloccata");
		
		statoFatturaRepository.save(stato);
		statoFatturaRepository.save(stato1);
		statoFatturaRepository.save(stato2);
		statoFatturaRepository.save(stato3);
		
	}

	public void caricaComuni() {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
        try (CSVReader reader = new CSVReaderBuilder(
                  new FileReader("comuniitaliani.csv"))
                  .withCSVParser(csvParser)
                  .withSkipLines(1)
                  .build()) {
            String[] values = null;

            while ((values = reader.readNext()) != null) {
            	Comune comune = new Comune();
            	comune.setNome(values[2]);
            	Provincia prov = provinciaRepo.findByNome(values[3]);
            	if(prov != null) {
            		comune.setProvincia(prov);
                	comuneRepo.save(comune);
            	}
            	
              
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
    
	}
}

