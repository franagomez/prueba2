package com.msclientes.ms_clientes.Controller;


import com.msclientes.ms_clientes.DTO.ClienteDTO;
import com.msclientes.ms_clientes.DTO.ClienteRequestDTO;
import com.msclientes.ms_clientes.Service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Listar cliente
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar(){

        List<ClienteDTO> clientes = clienteService.findAll();

        if (clientes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }

    // Buscar cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Integer id){

        return ResponseEntity.ok(clienteService.findById(id));
    }

    // Crear cliente
    @PostMapping
    public ResponseEntity<ClienteDTO> guardar(@Valid @RequestBody ClienteRequestDTO dto){

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(dto));
    }

    // Actualizar cliente

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ClienteRequestDTO dto) {

        return ResponseEntity.ok(clienteService.update(id, dto));
    }

    // Eliminar cliente

    @DeleteMapping("/{id}")

    public ResponseEntity<Void> eliminar(@PathVariable Integer id){

        boolean eliminado = clienteService.delete(id);

        if (!eliminado){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

}
