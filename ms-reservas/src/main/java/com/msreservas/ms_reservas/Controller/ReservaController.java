package com.msreservas.ms_reservas.Controller;


import com.msreservas.ms_reservas.DTO.ReservaDTO;
import com.msreservas.ms_reservas.DTO.ReservaRequestDTO;
import com.msreservas.ms_reservas.Service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> listar(){
        List<ReservaDTO> reservas = reservaService.findAll();

        if(reservas.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.ok(reservaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ReservaDTO> guardar(@Valid @RequestBody ReservaRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> actualizar(@PathVariable Integer id,
                                                 @Valid @RequestBody ReservaRequestDTO dto){
        return ResponseEntity.ok(reservaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        boolean eliminado = reservaService.delete(id);

        if(!eliminado){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/activas/dias-mayor/{dias}")
    public ResponseEntity<List<ReservaDTO>> buscarActivasPorDiasMayor(@PathVariable Integer dias){

        List<ReservaDTO> reservas = reservaService.buscarActivasPorDiasMayor(dias);

        if(reservas.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reservas);
    }
}
