package com.msreservas.ms_reservas.DTO;




import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoReservaRequestDTO {

    @NotBlank
    @Size(min = 3, max = 50)
    private String nombre;

    @NotBlank
    @Size(min = 5, max = 200)
    private String descripcion;

    @NotNull
    @PositiveOrZero
    private Integer prioridad;

    private boolean activo = true;

    @NotNull
    @PastOrPresent
    private LocalDate fechaCreacion;





}
