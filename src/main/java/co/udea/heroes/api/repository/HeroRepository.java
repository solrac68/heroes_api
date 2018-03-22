package co.udea.heroes.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import co.udea.heroes.api.domain.Hero;

public interface HeroRepository extends JpaRepository<Hero, String>{
	
	public Optional<Hero> findById(int id);
}
