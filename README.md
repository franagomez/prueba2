# Sistema de Arriendo de Vehículos — Arquitectura de Microservicios

## Integrantes
- Ghislaine Carrasco
- Francisca Gómez

## Contexto del proyecto

Sistema de gestión para una empresa de arriendo de vehículos, construido como una arquitectura de microservicios. Cada dominio del negocio (clientes, vehículos, reservas, 
pagos, sucursales, empleados y reportes) es un microservicio independiente con su propia base de datos, 
que se registra en un servidor de descubrimiento (Eureka) y se expone al exterior a través de un API Gateway único.

## Microservicios

| Microservicio | Carpeta | Puerto | Base de datos (MySQL) | Responsabilidad |
|---|---|---|---|---|
| ms-clientes | ms-clientes/ | 8081 | prueba1 | Clientes y sus direcciones |
| ms-vehiculos | ms-vehiculos/ | 8082 | prueba1 | Catálogo de vehículos |
| ms-reservas | ms-reservas/ | 8083 | prueba2 | Reservas de arriendo |
| ms-pagos | ms_pagos/ | 8084 | prueba3 | Pagos asociados a reservas |
| ms-sucursales | ms_sucursales/ | 8085 | prueba1 | Sucursales y regiones |
| ms-empleados | ms_empleados/ | 8086 | prueba1 | Empleados |
| ms-reportes | ms_reportes/ | 8087 | prueba4 | Reportes agregados (consume ms-reservas y ms-pagos vía Feign) |

Además:

| Servicio de infraestructura | Carpeta | Puerto | Rol |
|---|---|---|---|
| Eureka Server | eureka-server/ | 8761 | Registro y descubrimiento de servicios |
| API Gateway | api-gateway/ | 8080 | Punto de entrada único, enruta por path a cada microservicio |

> *Nota*: ms-clientes vivía originalmente suelto en la raíz del repositorio (sin carpeta propia). Se reorganizó a ms-clientes/ para que la estructura sea consistente con el resto de los microservicios.

## Tecnologías

- Java 21
- Spring Boot 3.5.14 (ms-clientes usa 4.0.6 — cada microservicio tiene su propio pom.xml y ciclo de vida independiente, no son módulos Maven de un mismo reactor)
- Spring Cloud Gateway (enrutamiento)
- Netflix Eureka (service discovery)
- Spring Cloud LoadBalancer (resuelve el name de cada Feign Client contra Eureka; necesario para que ms-reportes pueda llamar a ms-pagos y ms-reservas sin usar URLs fijas)
- Spring Data JPA + Hibernate
- MySQL (base de datos local, vía Laragon)
- Flyway (migraciones, en los servicios que lo usan)
- Spring HATEOAS (RepresentationModelAssembler, EntityModel/CollectionModel)
- springdoc-openapi / Swagger UI
- JUnit 5 + Mockito (tests de Service, Controller y Repository)
- H2 (base de datos en memoria, solo para tests con perfil test)
- OpenFeign (comunicación entre ms-reportes y otros microservicios)
- Maven

## Requisitos previos

- JDK 21 instalado
- IntelliJ IDEA
- Laragon con MySQL corriendo localmente (no se usa Docker en este proyecto)
- Acceso a internet la primera vez que se abre cada proyecto en IntelliJ (para que Maven descargue las dependencias)

## Cómo correr el proyecto (IntelliJ + Laragon, sin Docker)

1. Iniciar Laragon y arrancar MySQL desde su panel de control.
2. Crear las 4 bases de datos que usan los microservicios (por ejemplo con HeidiSQL/phpMyAdmin de Laragon, o por consola MySQL):
   sql
   CREATE DATABASE prueba1;
   CREATE DATABASE prueba2;
   CREATE DATABASE prueba3;
   CREATE DATABASE prueba4;

   Con ddl-auto: update (perfil dev), Hibernate crea las tablas automáticamente al levantar cada servicio contra estas bases ya creadas.
3. Abrir cada microservicio como proyecto independiente en IntelliJ (File > Open, apuntando a la carpeta de cada uno — cada carpeta tiene su propio pom.xml, no hay un proyecto padre que los agrupe). Puedes tenerlos todos abiertos como ventanas separadas de IntelliJ.
4. Configurar el perfil activo en cada Run Configuration de IntelliJ (clase *Application, ej. MsPagosApplication, MsClientesApplication, MsReservasApplication, etc.), escribiendo dev en el campo **"Active profiles"* (o agregando SPRING_PROFILES_ACTIVE=dev en "Environment variables"). **Esto aplica a los 7 microservicios*: ms-clientes, ms-vehiculos, ms-reservas, ms_pagos, ms_sucursales, ms_empleados y ms_reportes.
5. Orden de ejecución recomendado:
   1. eureka-server (puerto 8761) — primero siempre, para que el resto se pueda registrar. *Importante*: correr la clase Application (con main), no la clase de test ApplicationTests — esta última arranca y se cierra sola en segundos.
   2. api-gateway (puerto 8080).
   3. El resto de los microservicios, en cualquier orden — con la salvedad de que ms-reportes llama por Feign a ms-reservas y ms-pagos, así que conviene tenerlos arriba antes de probar los endpoints de reportes que dependen de ellos.
6. Verifica en [http://localhost:8761](http://localhost:8761) (panel de Eureka) que todos los servicios aparezcan registrados con estado UP antes de hacer pruebas entre microservicios.

## Perfiles de configuración

Los 7 microservicios (ms-clientes, ms-vehiculos, ms-reservas, ms_pagos, ms_sucursales, ms_empleados, ms_reportes) usan la misma estrategia de configuración, en formato .properties, dividida en 3 archivos por microservicio:

- *application.properties*: configuración común (nombre de la app, puerto). No fija un perfil activo por defecto — se elige explícitamente al ejecutar.
- *application-dev.properties*: perfil de desarrollo. Datasource MySQL real apuntando a Laragon (localhost:3306), ddl-auto=update, show-sql=true, registro en Eureka, Swagger habilitado.
- *application-test.properties*: perfil usado por los tests automatizados (@DataJpaTest, @WebMvcTest, etc.). Base de datos H2 en memoria, ddl-auto=create-drop, sin Eureka, para que los tests corran de forma aislada sin depender de MySQL ni del resto de los servicios.

Para correr en modo desarrollo: SPRING_PROFILES_ACTIVE=dev (variable de entorno), o en IntelliJ, en el campo *"Active profiles"* del Run Configuration, escribir dev.

## Configuración del API Gateway

El API Gateway (api-gateway/src/main/resources/application.yaml, puerto 8080) enruta por prefijo de path hacia cada microservicio:

| Ruta | Redirige a |
|---|---|
| /api/v1/clientes/** | ms-clientes (localhost:8081) |
| /api/v1/vehiculos/** | ms-vehiculos (localhost:8082) |
| /api/v1/reservas/** | ms-reservas (localhost:8083) |
| /api/v1/pagos/** | ms-pagos (localhost:8084) |
| /api/v1/sucursales/** | ms-sucursales (localhost:8085) |
| /api/v1/empleados/** | ms-empleados (localhost:8086) |
| /api/v1/reportes/** | ms-reportes (localhost:8087) |
| /api/v1/regiones/** | ms-sucursales (localhost:8085) |

Es decir, cualquier endpoint puede probarse tanto directo contra el puerto del microservicio como a través de http://localhost:8080/api/v1/... vía el Gateway.

## Acceso a Swagger

Cada microservicio expone su propia documentación OpenAPI/Swagger en su propio puerto:

- ms-clientes: http://localhost:8081/swagger-ui/index.html
- ms-vehiculos: http://localhost:8082/swagger-ui/index.html
- ms-reservas:http://localhost:8083/swagger-ui/index.html
- ms-pagos: http://localhost:8084/doc/swagger-ui/index.html
- ms-sucursales: http://localhost:8085/swagger-ui/index.html
- ms-empleados: http://localhost:8086/swagger-ui/index.html
- ms-reportes: http://localhost:8087/doc/swagger-ui.html / http://localhost:8087/doc/swagger-ui/index.html


## Cómo probar los endpoints

- Con todos los servicios arriba (ver orden de ejecución), abre el Swagger UI del microservicio que quieras probar y ejecuta los endpoints directamente desde ahí ("Try it out").
- Alternativamente, usa Postman, Insomnia o curl contra:
   - El microservicio directo, ej.: curl http://localhost:8086/api/v1/empleados
   - O a través del Gateway, ej.: curl http://localhost:8080/api/v1/empleados
- Las respuestas de listado y detalle incluyen enlaces HATEOAS (_links, _embedded) generados por los RepresentationModelAssembler de cada entidad.
- Los errores (404, 400, 500) devuelven un cuerpo JSON consistente con timestamp, status, mensaje y path, generado por el GlobalExceptionHandler de cada servicio.
- Cada uno de los 7 microservicios carga automáticamente al arrancar (vía CommandLineRunner) al menos 5 registros de ejemplo por entidad, si la base de datos está vacía — así se puede probar sin cargar datos a mano.

## Colección Postman

La colección con las peticiones principales de los 7 microservicios está en Postman/Sistema Arriendo de Vehículos.postman_collection.json. Impórtala en Postman (botón "Import").

Requiere que Eureka y los 7 microservicios estén corriendo con el perfil dev activo antes de probar, especialmente para los endpoints de ms-reportes que consumen ms-pagos y ms-reservas vía Feign.

## Trabajo colaborativo (Trello)

Tablero del proyecto: [Trello — Sistema de Arriendo de Vehículos](https://trello.com/b/IfQGC56U/sistema-de-arriendo-de-vehiculos)

El trabajo se organizó mediante tarjetas técnicas con responsables, descripciones y listas de verificación. Las tareas abarcan arquitectura, persistencia, entidades, patrón CSR, DTOs, CRUD, consultas personalizadas, reglas de negocio, Feign Client, Eureka, API Gateway, Swagger/OpenAPI, HATEOAS, pruebas automatizadas, GitHub, documentación y validación final.

Las tarjetas finalizadas permiten identificar los aportes individuales y colaborativos de ambas integrantes.

## Tests

Cada microservicio incluye tests de Service (Mockito), Controller (@WebMvcTest + MockMvc) y Repository (@DataJpaTest con H2, perfil test).

Para correrlos desde la carpeta del microservicio:

```bash
./mvnw test -Dspring.profiles.active=test
```