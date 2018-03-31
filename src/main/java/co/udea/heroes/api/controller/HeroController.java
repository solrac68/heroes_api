package co.udea.heroes.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.udea.heroes.api.domain.Hero;
import co.udea.heroes.api.exception.DataNotFoundException;
import co.udea.heroes.api.service.HeroService;
import co.udea.heroes.api.util.Messages;

@RestController
@RequestMapping("/tourofheroes")
public class HeroController {
	
	private static Logger log = LoggerFactory.getLogger(HeroController.class);
	
	@Autowired
    private Messages messages;	

	@Autowired
	@Qualifier("HeroServiceImpl")
	private HeroService heroService;

	
	@RequestMapping("listar")
	public List<Hero> getHeroes(){
		return heroService.getHeroes();		
	}
	
	@RequestMapping("consultar")
	public Hero getHero(int id) throws DataNotFoundException{
		log.debug("Entro a consultar");
		Optional<Hero> hero = heroService.getHero(id);
		if(!hero.isPresent()){
			throw new DataNotFoundException(messages.get("exception.data_not_found.hero"));
		}
		return hero.get();
	}
	
	@RequestMapping("consultar404")
	public Hero getHeroNo404(int id){
		log.debug("Entro a consultar");
		Hero heroe;
		Optional<Hero> hero = heroService.getHero(id);
		if(!hero.isPresent()){
			heroe = new Hero();
		}
		else {
			heroe = hero.get();
		}
		return heroe;
	}
	
	
	@PostMapping("/crear")
	public Hero addHero(@Valid @RequestBody Hero heroe) {
		return heroService.saveHero(heroe);
	}
	
	
	@PutMapping("/actualizar")
	public Hero updateHero(@Valid @RequestBody Hero heroDetails) {
		
		Hero heroe = heroService.getHero(heroDetails.getId())
				.orElseThrow(() -> new DataNotFoundException(messages.get("exception.data_not_found.hero")));
		
		heroe.setName(heroDetails.getName());
		
		Hero updatedHero = heroService.saveHero(heroe);
		
		return updatedHero;
	}
	
	@DeleteMapping("/borrar/{id}")
	public ResponseEntity<?> deleteHero(@PathVariable(value = "id") int id) {
		
		Hero heroe = heroService.getHero(id)
				.orElseThrow(() -> new DataNotFoundException(messages.get("exception.data_not_found.hero")));
		
		heroService.deleteHero(heroe);
		
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping("buscar")
	public List<Hero> searchHeroes(String name) {
		name = "%".concat(name).concat("%");
		return heroService.getHeroes(name);
	}
	
	
	
}
