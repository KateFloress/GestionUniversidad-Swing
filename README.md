# 🎓 Sistema de Gestión Universitaria (Java Swing + MySQL)

Aplicación de escritorio desarrollada en **Java** para gestionar calificaciones académicas entre docentes y estudiantes contra una base de datos relacional (**MySQL / MariaDB**). Proyecto realizado dentro del ciclo superior de **Desarrollo de Aplicaciones Multiplataforma (DAM)**.

## 🚀 Funcionalidades Principales

* 🔐 **Inicio de Sesión por Roles (`VentanaLogeo`):** Autenticación de usuario y contraseña filtrando por perfil (*Profesor/a* o *Alumno/a*) con validación de campos.
* 👨‍🏫 **Panel del Profesor (`VentanaProfe`):** Carga dinámica de los módulos impartidos por el docente, visualización de alumnos matriculados en `JTable` e inserción, modificación y eliminación de notas (0 a 10).
* 👩‍🎓 **Panel del Alumno (`VentanAlum`):** Consulta de calificaciones filtrando por módulo individual o visualizando el boletín completo de todas las asignaturas matriculadas.
* 🛡️ **Persistencia Segura (`GestionBD` y `Conexion`):** Consultas parametrizadas mediante `PreparedStatement` y gestión automática de conexiones con `try-with-resources`.

## 📂 Estructura del Proyecto

* **`vista`:** Interfaces gráficas Swing diseñadas con WindowBuilder (`VentanaLogeo`, `VentanaProfe`, `VentanAlum`).
* **`baseDatos`:** Conexión JDBC (`Conexion`) y operaciones CRUD contra la base de datos (`GestionBD`).
* **`imagenes`:** Recursos gráficos e iconos de la aplicación.

## 🛠️ Tecnologías Utilizadas

* Java
* Java Swing
* WindowBuilder
* JDBC & MySQL
* Eclipse IDE / phpMyAdmin

## 🗄️ Base de Datos (`universidad_db`)

El proyecto trabaja sobre tres tablas relacionales principales:
* **`usuarios`:** Almacena credenciales y rol (`usuario`, `password`, `cargo`).
* **`modulos`:** Registra las asignaturas y el docente asignado (`id_modulo`, `nombre_modulo`, `profesor_usuario`).
* **`matriculas`:** Relaciona alumnos, módulos y calificaciones (`id_matricula`, `alumno_usuario`, `id_modulo`, `nota`).

## 📸 Capturas de la Aplicación


<img width="452" height="304" alt="Captura de pantalla 2026-11-27 a las 1 22 10" src="https://github.com/user-attachments/assets/ff9a33b1-fc2c-4678-a12a-507161a13f18" />

<img width="478" height="491" alt="Captura de pantalla 2026-11-27 a las 1 22 48" src="https://github.com/user-attachments/assets/de1cfea7-a95b-46a6-882b-e6da222aeabd" />

<img width="563" height="319" alt="Captura de pantalla 2026-11-27 a las 1 23 10" src="https://github.com/user-attachments/assets/92b8a3d1-088e-4af1-9556-f3af0d774ee6" />

<img width="457" height="478" alt="Captura de pantalla 2026-11-27 a las 1 23 38" src="https://github.com/user-attachments/assets/57fa417f-3efc-40d3-9736-6d038828148a" />

<img width="700" height="344" alt="Captura de pantalla 2026-11-27 a las 1 23 58" src="https://github.com/user-attachments/assets/2c55fb14-6423-4bb2-96b9-4d30a3f17a33" />
