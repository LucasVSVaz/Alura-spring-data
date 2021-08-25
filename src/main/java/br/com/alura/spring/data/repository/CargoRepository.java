package br.com.alura.spring.data.repository;

import br.com.alura.spring.data.entities.Cargo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends CrudRepository<Cargo, Integer> {
}
