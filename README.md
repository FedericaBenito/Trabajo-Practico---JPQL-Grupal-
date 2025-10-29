# Trabajo Práctico: Sistema de Gestión de Facturas con JPA y JPQL

Este proyecto es un sistema de gestión de facturas desarrollado utilizando **Java Persistence API (JPA)** y **JPQL (Java Persistence Query Language)**. El sistema permite gestionar clientes, artículos, facturas y sus detalles, y realizar consultas complejas sobre los datos almacenados.

---

## Datos equipo
- Comision: 3k10
- Integrantes:
  -   Cecilia Calvo
  -   Gabriel Villalobos
  -   Laureano Rojas
  -   Marcos del Bosco
  -   Federica Benito

---


## Descripción del Proyecto

El sistema está diseñado para manejar las siguientes entidades principales:
- **Cliente**: Representa a los clientes del sistema.
- **Artículo**: Representa los artículos que pueden ser vendidos (pueden ser insumos o manufacturados).
- **Factura**: Representa las facturas emitidas a los clientes.
- **FacturaDetalle**: Representa los detalles de cada factura, incluyendo los artículos vendidos y sus cantidades.

---

## Tecnologías Utilizadas

- **Java 8+**
- **JPA (Java Persistence API)**
- **Hibernate** como implementación de JPA.
- **H2 Database** para almacenamiento de datos.
- **Lombok** para reducir código repetitivo (getters, setters, constructores, etc.).
- **JPQL** para realizar consultas complejas sobre las entidades.

---

## Estructura del Proyecto

- **`org.example`**: Contiene las entidades del modelo (Cliente, Artículo, Factura, FacturaDetalle, etc.).
- **`managers`**: Contiene las clases que gestionan las operaciones de persistencia y las consultas JPQL.
- **`Main.java`**: Clase principal que demuestra el uso de las entidades y las operaciones de persistencia.
- **`MainConsultasJPQL.java`**: Clase que contiene ejemplos de consultas JPQL implementadas.
- **`persistence.xml`**: Archivo de configuración de JPA.

---

## Consultas JPQL Implementadas

Se implementaron varias consultas JPQL para demostrar el manejo de datos y relaciones entre entidades:

1. **Listar todos los clientes**.
2. **Listar todas las facturas generadas en el último mes**.
3. **Obtener el cliente que ha generado más facturas**.
4. **Listar los artículos más vendidos**.
5. **Listar las facturas emitidas en los últimos 3 meses de un cliente específico**.
6. **Calcular el monto total facturado por un cliente**.
7. **Listar los artículos vendidos en una factura específica**.
8. **Obtener el artículo más caro vendido en una factura**.
9. **Contar la cantidad total de facturas generadas en el sistema**.
10. **Listar las facturas cuyo total es mayor a un valor determinado**.
11. **Consultar las facturas que contienen un artículo específico, filtrando por el nombre del artículo**.
12. **Listar los artículos filtrando por código parcial**.
13. **Listar todos los artículos cuyo precio sea mayor que el promedio de los precios de todos los artículos**.
14. **Listar los clientes que tienen al menos una factura (usando `EXISTS`)**.
