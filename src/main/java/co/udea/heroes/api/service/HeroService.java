package co.udea.heroes.api.service;

import java.util.List;
import java.util.Optional;

import co.udea.heroes.api.domain.Hero;

public interface HeroService {
	
	public List<Hero> getHeroes();

	public Optional<Hero> getHero(int id);
	
	public Hero saveHero(Hero heroe);
	
	public void deleteHero(Hero heroe);
	
	public List<Hero> getHeroes(String patron);
	

}
