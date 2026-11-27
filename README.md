# Sistema de Gestión Universitaria (Java Swing + MySQL)

Aplicación de escritorio desarrollada en **Java** con **Swing** y **JDBC** para la gestión académica de calificaciones entre profesores y alumnos contra una base de datos **MySQL** (`universidad_db`).

## Funcionalidades Principales

* **Autenticación por Roles (`VentanaLogeo`):** Inicio de sesión con validación de credenciales y redirección automática según el cargo (*Profesor/a* o *Alumno/a*).
* **Panel del Profesor (`VentanaProfe`):** Carga dinámica de los módulos impartidos por el docente, visualización de alumnos matriculados en `JTable` e inserción, actualización y eliminación de notas (0 a 10).
* **Panel del Alumno (`VentanAlum`):** Consulta de calificaciones filtrando por módulo individual o visualizando el boletín completo de todas las asignaturas matriculadas.
* **Persistencia Segura (`GestionBD` y `Conexion`):** Consultas parametrizadas mediante `PreparedStatement` y gestión automática de recursos con `try-with-resources`.

## Estructura del Proyecto

* **`vista`:** Interfaces gráficas Swing (`VentanaLogeo`, `VentanaProfe`, `VentanAlum`).
* **`baseDatos`:** Conexión JDBC (`Conexion`) y operaciones CRUD contra MySQL (`GestionBD`).
* **`imagenes`:** Recursos gráficos e iconos de la interfaz.

## Tecnologías Utilizadas

* Java SE (Swing / AWT)
* JDBC & MySQL / MariaDB
* Eclipse IDE

## Capturas de Aplicación

<img width="700" height="344" alt="Captura de pantalla 2026-11-27 a las 1 23 58" src="https://github.com/user-attachments/assets/2c55fb14-6423-4bb2-96b9-4d30a3f17a33" />

<img width="457" height="478" alt="Captura de pantalla 2026-11-27 a las 1 23 38" src="https://github.com/user-attachments/assets/57fa417f-3efc-40d3-9736-6d038828148a" />

<img width="563" height="319" alt="Captura de pantalla 2026-11-27 a las 1 23 10" src="https://github.com/user-attachments/assets/92b8a3d1-088e-4af1-9556-f3af0d774ee6" />

<img width="478" height="491" alt="Captura de pantalla 2026-11-27 a las 1 22 48" src="https://github.com/user-attachments/assets/de1cfea7-a95b-46a6-882b-e6da222aeabd" />

<img width="455" height="308" alt="Captura de pantalla 2026-11-27 a las 1 14 14" src="https://github.com/user-attachments/assets/173f626f-1ecf-430e-9aa7-e7c8ea939b64" />
