package com.msclientes.ms_clientes.Runner;

import com.msclientes.ms_clientes.Model.Cliente;
import com.msclientes.ms_clientes.Model.Direccion;
import com.msclientes.ms_clientes.Repository.ClienteRepository;
import com.msclientes.ms_clientes.Repository.DireccionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final DireccionRepository direccionRepository;

    public DataLoader(ClienteRepository clienteRepository, DireccionRepository direccionRepository) {
        this.clienteRepository = clienteRepository;
        this.direccionRepository = direccionRepository;
    }

    @Override
    public void run(String... args) {
        if (clienteRepository.count() > 0) {
            log.info("Ya existen clientes cargados, se omite la carga inicial de datos");
            return;
        }

        log.info("Cargando datos iniciales de clientes y direcciones");

        Cliente c1 = crearCliente("11111111-1", "Marco", "Carrasco", "mluis.carr@gmail.com", "+56911111111", 100);
        Cliente c2 = crearCliente("22222222-2", "Francisca", "Gómez", "francisca.gomez@gmail.com", "+56922222222", 50);
        Cliente c3 = crearCliente("33333333-3", "Ghislaine", "Carrasco", "ghislaine.c@gmail.com", "+56933333333", 200);
        Cliente c4 = crearCliente("44444444-4", "Pedro", "Soto", "pedro.soto@gmail.com", "+56944444444", 0);
        Cliente c5 = crearCliente("55555555-5", "Valentina", "Rojas", "valentina.rojas@gmail.com", "+56955555555", 75);

        clienteRepository.saveAll(java.util.List.of(c1, c2, c3, c4, c5));
        log.info("Se cargaron {} clientes de prueba", clienteRepository.count());

        direccionRepository.saveAll(java.util.List.of(
                crearDireccion("Av. Providencia", "1234", "Providencia", "Santiago", 7500000, true, c1),
                crearDireccion("Los Leones", "567", "Providencia", "Santiago", 7500001, false, c1),
                crearDireccion("Av. Apoquindo", "3400", "Las Condes", "Santiago", 7550000, true, c2),
                crearDireccion("Av. Vitacura", "8900", "Vitacura", "Santiago", 7630000, true, c3),
                crearDireccion("Camino a Farellones", "120", "Vitacura", "Santiago", 7630001, false, c3),
                crearDireccion("Av. Colón", "4500", "Concepción", "Concepción", 4030000, true, c4),
                crearDireccion("Av. Alemania", "780", "Temuco", "Temuco", 4780000, true, c5)
        ));
        log.info("Se cargaron {} direcciones de prueba", direccionRepository.count());
    }

    private Cliente crearCliente(String run, String nombre, String apellido, String email,
                                 String telefono, int puntosCliente) {
        Cliente cliente = new Cliente();
        cliente.setRun(run);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        cliente.setPuntosCliente(puntosCliente);
        cliente.setActivo(true);
        cliente.setFechaRegistro(LocalDate.now());
        return cliente;
    }

    private Direccion crearDireccion(String calle, String numero, String comuna, String ciudad,
                                     int codigoPostal, boolean principal, Cliente cliente) {
        Direccion direccion = new Direccion();
        direccion.setCalle(calle);
        direccion.setNumero(numero);
        direccion.setComuna(comuna);
        direccion.setCiudad(ciudad);
        direccion.setCodigoPostal(codigoPostal);
        direccion.setPrincipal(principal);
        direccion.setFechaRegistro(LocalDate.now());
        direccion.setCliente(cliente);
        return direccion;
    }
}


