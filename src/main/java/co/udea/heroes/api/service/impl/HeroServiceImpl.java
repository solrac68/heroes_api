package co.udea.heroes.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.udea.heroes.api.domain.Hero;
import co.udea.heroes.api.repository.HeroRepository;
import co.udea.heroes.api.service.HeroService;

@Service
@Qualifier("HeroServiceImpl")
public class HeroServiceImpl implements HeroService {
	
	private HeroRepository heroRepository;
	
	public HeroServiceImpl(HeroRepository heroRepository){
		this.heroRepository = heroRepository;
	}
	
	@Override
	public List<Hero> getHeroes() {
		return heroRepository.findAll();
	}

	@Override
	public Optional<Hero> getHero(int id) {
		return heroRepository.findById(id);
	}
	
	@Override
	public Hero saveHero(Hero heroe) {
		return heroRepository.save(heroe);
	}
	
	@Override
	public void deleteHero(Hero heroe) {
		 heroRepository.delete(heroe);
	}

	@Override
	public List<Hero> getHeroes(String patron) {
		// TODO Auto-generated method stub
		return heroRepository.findBynameLike(patron);
	}

//	@Override
//	public List<Hero> getHeroes(String patron) {
//		// TODO Auto-generated method stub
//		heroRepository.findAll(example)
//		return null;
//	}
	

}

