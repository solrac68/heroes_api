package co.udea.heroes.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.udea.heroes.api.domain.Hero;

public interface HeroRepository extends JpaRepository<Hero, String>{

}
