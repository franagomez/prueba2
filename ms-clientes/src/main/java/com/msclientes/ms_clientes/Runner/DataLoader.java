package com.msclientes.ms_clientes.Runner;

import com.msclientes.ms_clientes.Model.Cliente;
import com.msclientes.ms_clientes.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void run(String... args) throws Exception {
        if (clienteRepository.count() == 0) {

            Cliente cliente = new Cliente();

            cliente.setRun("1111111111-1");
            cliente.setNombre("Marco Luis");
            cliente.setApellido("Carrasco");
            cliente.setEmail("mluis.carr@gmail.com");
            cliente.setTelefono("+569 11111111");
            cliente.setPuntosCliente(100);
            cliente.setActivo(true);
            cliente.setFechaRegistro(LocalDate.now());

            clienteRepository.save(cliente);

            System.out.println("Cliente de prueba");
        }
    }
}
