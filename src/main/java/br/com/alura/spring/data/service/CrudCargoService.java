package br.com.alura.spring.data.service;

import br.com.alura.spring.data.entities.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;

@Service
public class CrudCargoService {
    private final CargoRepository cargoRepository;

    private Boolean system = true;
    private Scanner scanner = new Scanner(System.in);

    private String erroId = "ERRO - ID não encontrado";

    public CrudCargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }


    public void menuCargo() {
        Integer escolha;
        while (system) {
            System.out.println("Informe a opção desejada: \n"
                    + "1- Adicionar \n"
                    + "2- Remover \n"
                    + "3- Atualizar \n"
                    + "4- Listar \n"
                    + "5- Sair \n");
            escolha = scanner.nextInt();
            switch (escolha) {
                case 1:
                    addCargo(scanner);
                    break;
                case 2:
                    removeCargo(scanner);
                    break;
                case 3:
                    alteraCargo(scanner);
                    break;
                case 4:
                    listaCargo();
                    break;
                case 5:
                    System.out.println("Obrigado por cadastrar os cargos. \n");
                    system = false;
                    break;
                default:
                    System.out.println("ERRO - Opção inválida");
            }
        }
    }


    public String addCargo(Scanner scanner) {
        Cargo cargo = new Cargo();
        System.out.println("Informe a descrição do cargo: ");
        scanner.nextLine();
        cargo.setDescricao(scanner.nextLine());
        cargoRepository.save(cargo);
        return "Cargo salvo com sucesso. Nome: " + cargo.getDescricao();
    }

    public void removeCargo(Scanner scanner) {
        System.out.println("Informe o ID do cargo a ser deletado: ");
        Integer idDelete = scanner.nextInt();
        Optional<Cargo> deleteId = cargoRepository.findById(idDelete);
        if (!deleteId.isEmpty()) {
            cargoRepository.deleteById(idDelete);
            System.out.println("Cargo deletado com sucesso.");
        } else {
            System.out.println(erroId);
        }
    }

    public void alteraCargo(Scanner scanner) {
        Cargo cargo = new Cargo();
        System.out.println("Informe o ID do cargo a ser alterado: ");
        Integer idAltera = scanner.nextInt();
        Optional<Cargo> alteraId = cargoRepository.findById(idAltera);
        if (!alteraId.isEmpty()) {
            System.out.println("Informe a nova descrição do cargo: ");
            scanner.nextLine();
            cargo.setDescricao(scanner.nextLine());
            cargo.setId(idAltera);
            cargoRepository.save(cargo);
            System.out.println("Descrição do cargo alterada com sucesso.");
        } else {
            System.out.println(erroId);
        }
    }

    public void listaCargo() {
        Iterable<Cargo> listaCargo = cargoRepository.findAll();
        listaCargo.forEach(c -> System.out.println(c));
    }
}
