# Sistema de Inventario - Desarrollo Web Backend

Este proyecto forma parte de la materia **Web Client and Backend Development Frameworks**. Consiste en una aplicación de gestión de inventario desarrollada en Java, utilizando una arquitectura de acceso a datos para interactuar con un servidor MySQL.

## 🛠️ Entorno de Desarrollo
* **Sistema Operativo:** Windows 11
* **IDE:** IntelliJ IDEA 2025.3.2
* **Lenguaje:** Java 25 (JDK 25.0.2)
* **Gestor de Dependencias:** Maven
* **Base de Datos:** MySQL 8.0

## 🗃️ Configuración de la Base de Datos
Para replicar el entorno, ejecuta los siguientes comandos en tu terminal de MySQL o Workbench:

```sql
-- Creación de la base de datos
CREATE DATABASE InventarioEliminar;
USE InventarioEliminar;

-- Creación de la tabla Categoria
CREATE TABLE Categoria (
    idCategoria INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombreCategoria VARCHAR(50) NOT NULL UNIQUE,
    descripcionCategoria VARCHAR(200) NOT NULL,
    create_at DATE DEFAULT (CURRENT_DATE())
);

-- Creación de la tabla Producto
CREATE TABLE Producto (
    idProducto INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombreProducto VARCHAR(50) NOT NULL,
    descripcionProducto VARCHAR(100),
    precioProducto DOUBLE,
    existencia INT,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    idCategoria INT,
    FOREIGN KEY (idCategoria) REFERENCES Categoria(idCategoria)
);