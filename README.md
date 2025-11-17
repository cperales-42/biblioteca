Biblioteca Spring Boot
Descripción del proyecto

Este proyecto es una aplicación web de gestión de biblioteca desarrollada con Spring Boot, Spring Security, Thymeleaf y JPA/Hibernate. Permite gestionar libros, usuarios y préstamos de manera sencilla y centralizada.

El objetivo principal es ofrecer un sistema donde los administradores puedan controlar los recursos de la biblioteca y los usuarios lectores puedan consultar y solicitar préstamos de libros. Se busca una experiencia intuitiva tanto para administración como para lectura.
Funcionalidades actuales

    Gestión de libros: crear, editar, listar y eliminar.
    Gestión de usuarios: registro, edición y eliminación (con roles ADMIN o LECTOR).
    Gestión de préstamos: prestar y devolver libros.
    Autenticación y roles usando Spring Security.

Estado actual (17/11/2025)

Actualmente el proyecto se encuentra en desarrollo, especialmente en el módulo de control de acceso por roles (ADMIN vs LECTOR). Esto significa que pueden existir problemas al intentar acceder a ciertas páginas, ya que estoy ajustando las restricciones de seguridad.

⚠️ Si intentas entrar con ciertos usuarios, puede que Spring Security no te permita acceder a algunas rutas hasta que se complete la configuración de roles.
Tecnologías utilizadas

    Java 17
    Spring Boot 3.x
    Spring Security
    Spring Data JPA / Hibernate
    Thymeleaf
    Base de datos MySQL

Conectar la base de datos (primitivo)
1. Crear la base de datos

mysql -u -p CREATE DATABASE biblioteca;
2. Seleccionar la base de datos

USE biblioteca;
3. Cargar los datos
SOURCE db/biblioteca.sql;
