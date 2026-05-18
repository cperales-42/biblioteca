# Biblioteca TFG

## Descripción del proyecto
Este proyecto es una aplicación web de gestión de biblioteca desarrollada con **Spring Boot**, **Thymeleaf** y **MySQL**. Permite gestionar libros, categorías, usuarios y préstamos de manera sencilla. La aplicación incluye autenticación y autorización según roles (ADMIN y LECTOR).

El objetivo principal del proyecto es crear un sistema funcional que permita a un administrador gestionar los recursos de la biblioteca (añadir, editar y eliminar libros, categorías y usuarios) y a los lectores consultar el catálogo y solicitar préstamos, con control estricto de disponibilidad de ejemplares y préstamos activos.

---

## Estado actual
A fecha de hoy (18 de Mayo de 2026), el proyecto se encuentra en una fase avanzada con las siguientes funcionalidades:

- **Autenticación y autorización**: Implementado mediante **Spring Security** con protección de rutas por roles.
- **Gestión de Usuarios**:
  - Registro público de nuevos lectores.
  - CRUD completo de usuarios para administradores.
- **Gestión del Catálogo**:
  - CRUD de **Categorías** (Novela, Ciencia Ficción, Historia, etc.).
  - CRUD de **Libros** vinculados a categorías.
  - Control automático de stock: los ejemplares disponibles se ajustan solos al cambiar los totales o realizar préstamos/devoluciones.
- **Sistema de Préstamos**:
  - Los lectores pueden solicitar un préstamo si no tienen uno pendiente.
  - Validación visual: El botón de "Pedir prestado" se bloquea y cambia de estado si el libro no tiene stock o el usuario ya tiene un préstamo activo.
  - Registro de devoluciones con actualización inmediata de disponibilidad.
- **Interfaz y Seguridad**:
  - Diseño limpio con **Bootstrap**.
  - Encriptación de contraseñas con **BCrypt**.
  - Despliegue simplificado mediante **Docker**.

---

## Tecnologías usadas
- Java 17
- Spring Boot 3.2.0
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL 8 (Dockerizado)
- Maven
- Bootstrap 5

---

## Cómo ejecutar el proyecto

### Opción A: Usando Docker (Recomendado)
Si tienes Docker instalado, puedes levantar la base de datos con un solo comando:

```bash
docker-compose up -d
```
Esto creará el contenedor de MySQL y cargará automáticamente la estructura y los datos iniciales desde `db/biblioteca.sql`.

### Opción B: Configuración manual
1. Crea la base de datos en tu servidor MySQL local:
   ```sql
   CREATE DATABASE biblioteca;
   ```
2. Importa el archivo de datos:
   ```bash
   mysql -u <usuario> -p biblioteca < db/biblioteca.sql
   ```
3. Revisa el archivo `src/main/resources/application.properties` para asegurar que el usuario y la contraseña coincidan con tu configuración local.

---

## Usuarios de prueba
Puedes acceder al sistema con las siguientes credenciales preconfiguradas:

- **Administrador**:
  - **Correo**: admin@email.com
  - **Contraseña**: cperales
- **Lector**:
  - **Correo**: lector@email.com
  - **Contraseña**: cperales

*(Nota: También puedes registrar un nuevo usuario desde la pantalla de registro).*
