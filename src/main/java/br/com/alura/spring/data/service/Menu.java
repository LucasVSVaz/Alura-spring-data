package br.com.alura.spring.data.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class Menu {
    private final CrudCargoService crudCargoService;
    private final CrudFuncionarioService crudFuncionarioService;

    private Boolean system = true;
    private Scanner scanner = new Scanner(System.in);

    public Menu(CrudCargoService crudCargoService, CrudFuncionarioService crudFuncionarioService) {
        this.crudCargoService = crudCargoService;
        this.crudFuncionarioService = crudFuncionarioService;
    }

    public void menuGeral() {
        Integer escolha;
        while (system) {
            System.out.println("Informe a opção desejada: \n"
                    + "1- Cargo \n"
                    + "2- Funcionário \n"
                    + "3- Sair \n");
            escolha = scanner.nextInt();
            switch (escolha) {
                case 1:
                    crudCargoService.menuCargo();
                    break;
                case 2:
                    crudFuncionarioService.menuFuncionario();
                    break;
                case 3:
                    System.out.println("Obrigado por utilizar o sistema");
                    system = false;
                    break;
                default:
                    System.out.println("ERRO - Opção inválida");
            }
        }
    }
}

