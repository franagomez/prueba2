INSERT INTO estado_reserva
(nombre, descripcion, prioridad, activo, fecha_creacion)
VALUES
    ('Pendiente', 'Reserva creada y pendiente de confirmación', 1, true, CURDATE()),
    ('Confirmada', 'Reserva confirmada por el sistema', 2, true, CURDATE()),
    ('Finalizada', 'Reserva completada exitosamente', 3, true, CURDATE()),
    ('Cancelada', 'Reserva cancelada por el cliente', 4, true, CURDATE());