# Biblioteca TFG

## Descripción del proyecto
Este proyecto es una aplicación web de gestión de biblioteca desarrollada con **Spring Boot**, **Thymeleaf** y **MySQL**. Permite gestionar libros, usuarios y préstamos de manera sencilla. La aplicación incluye autenticación y autorización según roles (ADMIN y LECTOR).

El objetivo principal del proyecto es crear un sistema funcional que permita a un administrador gestionar los recursos de la biblioteca y a los lectores consultar y pedir prestados libros, con control de disponibilidad.

---

## Estado actual
A fecha de hoy (17 de noviembre de 2025), el proyecto funciona, pero existen problemas de acceso a algunas páginas debido a la seguridad basada en roles. Estoy trabajando en la configuración para que solo los usuarios con el rol adecuado puedan acceder a ciertas rutas.

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
USE DATABASE biblioteca;
SOURCE db/biblioteca.sql;


