package br.com.alura.spring.data.repository;

import br.com.alura.spring.data.entities.Funcionario;
import br.com.alura.spring.data.entities.FuncionarioProjecao;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Integer>
        , JpaSpecificationExecutor<Funcionario> {
    List<Funcionario> findByNome(String nome);

    @Query("SELECT f.nome, f.cargo FROM Funcionario f")
    List<FuncionarioProjecao> findFuncionarioNomeCargo();

}
