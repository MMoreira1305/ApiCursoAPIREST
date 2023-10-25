package br.com.projeto.api.domain.servico;

import br.com.projeto.api.domain.dto.CarroDTO;
import br.com.projeto.api.domain.exception.ObjectNotFoundException;
import br.com.projeto.api.domain.model.Carro;
import br.com.projeto.api.domain.repositorio.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepo;

    public List<CarroDTO> getCarros(){
        return carroRepo.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public CarroDTO getCarroById(Long id) {
        Optional<Carro> carro = carroRepo.findById(id);
        return carro.map(CarroDTO::create).orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
 //       Optional<Carro> carro = carroRepo.findById(id);
 //       if (carro.isPresent()){
 //           return Optional.of(new CarroDTO(carro.get()));
 //       }else{
 //           return null;
 //       }
    }

    public List<CarroDTO> getCarrosByTipo(String tipo){

        return carroRepo.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
//        List<CarroDTO> carrosDTO = new ArrayList<>();
//
//        for(Carro c : carros){
//           carrosDTO.add(new CarroDTO(c));
//        }
    }

    public CarroDTO cadastrarVeiculo(Carro carro) {
        carroRepo.save(carro);
        CarroDTO c = CarroDTO.create(carro);
        return c;
    }

    public CarroDTO updateVeiculo(Long id, Carro carro){
        Assert.notNull(id, "Não foi possível atualizar o registro");

        // Busca o carro no banco de dados
        Optional<Carro> optional = carroRepo.findById(id);

        if (optional.isPresent()){
            Carro car = optional.get();
            car.setNome(carro.getNome());
            car.setTipo(carro.getTipo());
            System.out.println("Carro id: " + car.getId());

            // Atualiza o cadastro
            carroRepo.save(car);
            return CarroDTO.create(car);
        }else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public void deletar(Long id) {
        carroRepo.deleteById(id);
    }
}
