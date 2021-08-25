package br.com.alura.spring.data;

import br.com.alura.spring.data.service.CrudFuncionarioService;
import br.com.alura.spring.data.service.Menu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {

    private final Menu menu;
    private final CrudFuncionarioService crudFuncionarioService;

    public SpringDataApplication(Menu menu, CrudFuncionarioService crudFuncionarioService) {
        this.menu = menu;
        this.crudFuncionarioService = crudFuncionarioService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringDataApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        menu.menuGeral();
        crudFuncionarioService.listaFuncionarioNomeCargo();
    }
}
