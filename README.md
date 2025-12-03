# Biblioteca TFG

## Descripción del proyecto
Este proyecto es una aplicación web de gestión de biblioteca desarrollada con **Spring Boot**, **Thymeleaf** y **MySQL**. Permite gestionar libros, usuarios y préstamos de manera sencilla. La aplicación incluye autenticación y autorización según roles (ADMIN y LECTOR).

El objetivo principal del proyecto es crear un sistema funcional que permita a un administrador gestionar los recursos de la biblioteca (añadir, editar y eliminar libros y usuarios) y a los lectores consultar libros y solicitar préstamos, con control de disponibilidad de ejemplares y control de préstamos activos.

---

## Estado actual
A fecha de hoy (3 de diciembre de 2025), el proyecto es completamente funcional:

- Autenticación y autorización de usuarios mediante **Spring Security**.
- Roles implementados: **ADMIN** y **LECTOR**.
- Gestión de usuarios:
  - Registro de nuevos usuarios.
  - Edición y eliminación de usuarios por administradores.
- Gestión de libros:
  - Creación, actualización y eliminación de libros por administradores.
  - Visualización de la lista de libros.
  - Control automático de ejemplares disponibles al modificar los totales.
- Gestión de préstamos:
  - Los lectores pueden solicitar un préstamo si no tienen uno activo.
  - El botón de "Pedir prestado" cambia a rojo con el texto "Préstamo activo" cuando el usuario ya tiene un préstamo o no hay ejemplares disponibles.
  - Devolución de préstamos, incrementando automáticamente los ejemplares disponibles.
- Seguridad:
  - Rutas protegidas según roles.
  - Página de inicio adaptativa según el rol del usuario.
- Interfaz:
  - Listas de libros y préstamos con botones contextuales.
  - Formularios de creación y edición con validación básica.
  - Botón "Volver al inicio" disponible en páginas de listas.

El proyecto está listo para desplegar y usar como sistema básico de biblioteca.

---

## Tecnologías usadas
- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL 8
- Maven

---

## Cómo ejecutar el proyecto

### 1. Crear la base de datos
Abre una terminal MySQL y ejecuta:

```sql
mysql -u <usuario> -p
CREATE DATABASE biblioteca;
USE biblioteca;
SOURCE db/biblioteca.sql;

