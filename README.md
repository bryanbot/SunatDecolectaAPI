# SunatDecolectaAPI
En la presente REPO se ha construido un microservicio para consumir la API de consulta de información de Sunat brindada por Decolecta
# Microservicio Sunat-Consulta (Examen G14)

Este microservicio permite la consulta de RUCs a través de la API externa de **Decolecta**, integrando persistencia en base de datos, manejo de errores avanzado con OpenFeign y una estrategia de cache local.

## 1. Requisitos Previos
* **Java**: Versión 17.
* **Spring Boot**: Versión 3.4.2.
* **Base de Datos**: PostgreSQL (Base de datos: `bdjava14`).
* **Maven**: Para la gestión de dependencias.

## 2. Configuración de Variables de Entorno
El sistema requiere un token de seguridad para comunicarse con la API de Decolecta. Configure la siguiente variable de entorno en su IDE o Sistema Operativo:

* **Variable**: `DECOLECTA_TOKEN`
* **Valor sugerido**: `sk_13369.SMAWFqqnwecEkkGasUUoecoVRCHZzmjP`

## 3. Estructura del Proyecto
El proyecto sigue el layout de paquetes solicitado:
* `com.codigo.sunat.config`: Configuración de Feign y Beans.
* `com.codigo.sunat.controller`: Endpoints REST propios.
* `com.codigo.sunat.client`: Cliente OpenFeign para API externa.
* `com.codigo.sunat.dto`: Uso de **records** para transferencia de datos.
* `com.codigo.sunat.entity`: Entidades JPA (`Company`, `Consulta`).
* `com.codigo.sunat.enums`: Enums para estados y condiciones.
* `com.codigo.sunat.exception`: Manejo global de excepciones.
* `com.codigo.sunat.mapper`: Conversión de DTO a Entidad.
* `com.codigo.sunat.repository`: Repositorios Spring Data JPA.
* `com.codigo.sunat.service`: Lógica de negocio y coordinación.

## 4. Funcionalidades de Bonus Implementadas
* **Cache Simple**: Si un RUC fue consultado en los últimos 10 minutos, el sistema retorna los datos de la BD y registra la consulta como `SUCCESS CACHE` sin llamar al proveedor.
* **Manejo de Status 401 y 5xx**: Se implementaron mensajes descriptivos para errores de autenticación y caídas del servidor externo.

## 5. Evidencias de Prueba (Scripts curl)

### Caso A: Consulta Exitosa (SUCCESS)
```bash
curl -X GET http://localhost:8080/api/sunat/ruc/20601030013

###Caso B: Validación de RUC (11 dígitos)
```bash
curl -X GET http://localhost:8080/api/sunat/ruc/12345

###Caso C: Error de Proveedor (RUC no válido)
```bash
curl -X GET http://localhost:8080/api/sunat/ruc/11111111111

###Caso D: Ver Historial de Consultas
```bash
curl -X GET http://localhost:8080/api/sunat/ruc/20601030013/consultas
