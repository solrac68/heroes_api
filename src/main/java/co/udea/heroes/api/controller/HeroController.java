package co.udea.heroes.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.udea.heroes.api.domain.Hero;
import co.udea.heroes.api.service.HeroService;

@RestController
@RequestMapping("/tourofheroes")
public class HeroController {
	
	
	@Autowired
	@Qualifier("HeroServiceImpl")
	private HeroService heroService;

	
	@RequestMapping("heroes")
	public List<Hero> getHeros(){
		return heroService.getHeroes();		
	}
	
}
