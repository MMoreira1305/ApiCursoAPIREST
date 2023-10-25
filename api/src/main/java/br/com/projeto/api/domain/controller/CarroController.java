package br.com.projeto.api.domain.controller;

import br.com.projeto.api.domain.dto.CarroDTO;
import br.com.projeto.api.domain.model.Carro;
import br.com.projeto.api.domain.servico.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/carros")
public class CarroController {
    @Autowired
    public CarroService carroService;

    @GetMapping
    public ResponseEntity<List<CarroDTO>> get(){

        return carroService.getCarros().isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(carroService.getCarros());
    }

    @PostMapping
    public ResponseEntity post(@RequestBody Carro carro){
        CarroDTO c = carroService.cadastrarVeiculo(carro);
        URI location = getUri(c.getId());
        return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id){
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable Long id , @RequestBody Carro carro){
        CarroDTO carroDTO = carroService.updateVeiculo(id, carro);

        return carroDTO != null ?
                ResponseEntity.ok(carroDTO) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        carroService.deletar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id){
        CarroDTO carro = carroService.getCarroById(id);

        return ResponseEntity.ok(carro);

//        return carro
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());

//        return carro.isPresent() ?
//                new ResponseEntity<>(carro, HttpStatus.FOUND) :
//                new ResponseEntity<>(carro, HttpStatus.NOT_FOUND);

    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity getByTipo(@PathVariable String tipo){
        List<CarroDTO> carros = carroService.getCarrosByTipo(tipo);
        return carros.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(carros);
    }
}
