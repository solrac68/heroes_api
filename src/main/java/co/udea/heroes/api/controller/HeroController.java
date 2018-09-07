package co.udea.heroes.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/tourofheroes")
public class HeroController {
	
	private static Logger log = LoggerFactory.getLogger(HeroController.class);
	
	@Autowired
    private Messages messages;	

	@Autowired
	@Qualifier("HeroServiceImpl")
	private HeroService heroService;

	
	@GetMapping("listar")
	@ApiOperation(value = "Buscar todos", response = Page.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Los heroes fueron buscados", response = Page.class),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
	public ResponseEntity<List<Hero>> getHeroes(){
		return ResponseEntity.ok(heroService.getHeroes());		
	}
	
	@GetMapping("consultar")
	@ApiOperation(value = "Buscar por id", response = Page.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Los heroes fueron buscados", response = Page.class),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
	public Hero getHero(int id) throws DataNotFoundException{
		log.debug("Entro a consultar");
		Optional<Hero> hero = heroService.getHero(id);
		if(!hero.isPresent()){
			throw new DataNotFoundException(messages.get("exception.data_not_found.hero"));
		}
		return hero.get();
	}
	
	@GetMapping("consultar404")
	@ApiOperation(value = "Buscar por id 404", response = Page.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Los heroes fueron buscados", response = Page.class),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
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
	@ApiOperation(value = "Crear", response = Page.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "El heroe fue creado", response = Page.class),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
	public Hero addHero(@Valid @RequestBody Hero heroe) {
		return heroService.saveHero(heroe);
	}
	
	
	@PutMapping("/actualizar")
	@ApiOperation(value = "Actualizado", response = Page.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "El heroe fue actualizado", response = Page.class),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
	public Hero updateHero(@Valid @RequestBody Hero heroDetails) {
		
		Hero heroe = heroService.getHero(heroDetails.getId())
				.orElseThrow(() -> new DataNotFoundException(messages.get("exception.data_not_found.hero")));
		
		heroe.setName(heroDetails.getName());
		
		Hero updatedHero = heroService.saveHero(heroe);
		
		return updatedHero;
	}
	
	@DeleteMapping("/borrar/{id}")
	@ApiOperation(value = "Eliminado", response = Page.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "El heroe fue eliminado", response = Page.class),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
	public ResponseEntity<?> deleteHero(@PathVariable(value = "id") int id) {
		
		Hero heroe = heroService.getHero(id)
				.orElseThrow(() -> new DataNotFoundException(messages.get("exception.data_not_found.hero")));
		
		heroService.deleteHero(heroe);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("buscar")
	@ApiOperation(value = "Busqueda", response = Page.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "El heroe fue encontrado", response = Page.class),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
	public List<Hero> searchHeroes(String name) {
		name = "%".concat(name).concat("%");
		return heroService.getHeroes(name);
	}
	
	
	
}
