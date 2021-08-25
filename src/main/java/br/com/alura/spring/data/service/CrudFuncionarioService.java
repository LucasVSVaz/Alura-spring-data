package br.com.alura.spring.data.service;

import br.com.alura.spring.data.entities.Cargo;
import br.com.alura.spring.data.entities.Funcionario;
import br.com.alura.spring.data.entities.FuncionarioProjecao;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class CrudFuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final CargoRepository cargoRepository;
    private final CrudCargoService crudCargoService;


    private Boolean system = true;
    private Scanner scanner = new Scanner(System.in);

    private String erroId = "ERRO - ID não encontrado";

    public CrudFuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository, CrudCargoService crudCargoService) {
        this.funcionarioRepository = funcionarioRepository;
        this.cargoRepository = cargoRepository;
        this.crudCargoService = crudCargoService;
    }


    public void menuFuncionario() {
        Integer escolha;
        while (system) {
            System.out.println("Informe a opção desejada: \n"
                    + "1- Adicionar \n"
                    + "2- Remover \n"
                    + "3- Atualiza \n"
                    + "4- Listar \n"
                    + "5- Pesquisar por nome \n"
                    + "6- Sair \n");
            escolha = scanner.nextInt();
            switch (escolha) {
                case 1:
                    addFuncionario(scanner);
                    break;
                case 2:
                    removeFuncionario(scanner);
                    break;
                case 3:
                    alteraFuncionario(scanner);
                    break;
                case 4:
                    listaFuncionario(scanner);
                    break;
                case 5:
                    listaPorNome(scanner);
                    break;
                case 6:
                    System.out.println("Obrigado por cadastrar os funcionários.");
                    system = false;
                    break;
                default:
                    System.out.println("ERRO - Opção inválida.");
            }
        }
    }


    public void addFuncionario(Scanner scanner) {
        Funcionario funcionario = new Funcionario();
        System.out.println("Informe o nome do Funcionário: ");
        scanner.nextLine();
        funcionario.setNome(scanner.nextLine());

        while (funcionario.getCargo() == null) {
            System.out.println("Informe o ID do cargo a ser vinculado: ");
            crudCargoService.listaCargo();
            Integer idCargo = scanner.nextInt();
            Optional<Cargo> cargo = cargoRepository.findById(idCargo);
            if (!cargo.isEmpty()) {
                funcionario.setCargo(cargo.get());
                funcionarioRepository.save(funcionario);
                System.out.println("Funcionário salvo com sucesso.");
            } else {
                System.out.println("ERRO - Cargo não vinculado");
            }
        }
    }

    public void removeFuncionario(Scanner scanner) {
        System.out.println("Informe o ID do funcionário a ser excluído: ");
        Integer idDelete = scanner.nextInt();
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idDelete);
        if (!funcionario.isEmpty()) {
            funcionarioRepository.deleteById(idDelete);
            System.out.println("Funcionário excluído com sucesso.");
        } else {
            System.out.println(erroId);
        }
    }

    public void alteraFuncionario(Scanner scanner) {
        Funcionario funcionario = new Funcionario();
        System.out.println("Informe o ID do funcionário a ser alterado: ");
        Iterable<Funcionario> listaFuncionario = funcionarioRepository.findAll();
        listaFuncionario.forEach(f -> System.out.println(f));
        Integer idAltera = scanner.nextInt();
        Optional<Funcionario> byId = funcionarioRepository.findById(idAltera);
        if (!byId.isEmpty()) {
            System.out.println("Informe o nome do Funcionário: ");
            scanner.nextLine();
            String nome = scanner.nextLine();
            System.out.println("Informe o ID do cargo a ser vinculado: ");
            crudCargoService.listaCargo();
            Integer cargoId = scanner.nextInt();
            Optional<Cargo> cargo = cargoRepository.findById(cargoId);
            funcionario.setId(idAltera);
            funcionario.setNome(nome);
            funcionario.setCargo(cargo.get());
            funcionarioRepository.save(funcionario);
        } else {
            System.out.println(erroId);
        }
    }

    public void listaFuncionario(Scanner scanner) {
        System.out.println("Informe qual página deseja visualizar");
        Integer page = scanner.nextInt();
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "nome"));
        Page<Funcionario> listaFuncionario = funcionarioRepository.findAll(pageable);
        listaFuncionario.forEach(f -> System.out.println(f));

    }

    public void listaPorNome(Scanner scanner) {
        System.out.println("Informe o nome a ser pesquisado: ");
        scanner.nextLine();
        String nome = scanner.nextLine().toUpperCase();
        List<Funcionario> byNome = funcionarioRepository.findByNome(nome);
        byNome.forEach(n -> System.out.println(n));
    }

    public void listaFuncionarioNomeCargo() {
        List<FuncionarioProjecao> lista = funcionarioRepository.findFuncionarioNomeCargo();
        lista.forEach(f -> System.out.println("Nome Funcionário: " + f.getNome() + " - Cargo: " + f.getCargo()));
    }

}
