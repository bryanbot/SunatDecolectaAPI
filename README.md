# SunatDecolectaAPI
En la presente REPO se ha construido un microservicio para consumir la API de consulta de información de Sunat brindada por Decolecta
# Microservicio Sunat-Consulta (Examen G14)

Este microservicio permite la consulta de RUCs a través de la API externa de **Decolecta**, integrando persistencia en base de datos, manejo de errores avanzado con OpenFeign y una estrategia de cache local.

## [cite_start]1. Requisitos Previos [cite: 30-33]
* [cite_start]**Java**: Versión 17[cite: 31].
* [cite_start]**Spring Boot**: Versión 3.4.2[cite: 32].
* **Base de Datos**: PostgreSQL (Base de datos: `bdjava14`).
* [cite_start]**Maven**: Para la gestión de dependencias[cite: 33].

## [cite_start]2. Configuración de Variables de Entorno [cite: 150, 166]
El sistema requiere un token de seguridad para comunicarse con la API de Decolecta. Configure la siguiente variable de entorno en su IDE o Sistema Operativo:

* [cite_start]**Variable**: `DECOLECTA_TOKEN` [cite: 150, 166]
* **Valor sugerido**: `sk_13369.SMAWFqqnwecEkkGasUUoecoVRCHZzmjP`

## [cite_start]3. Estructura del Proyecto [cite: 45-57]
El proyecto sigue el layout de paquetes solicitado:
* [cite_start]`com.codigo.sunat.config`: Configuración de Feign y Beans[cite: 48].
* [cite_start]`com.codigo.sunat.controller`: Endpoints REST propios[cite: 49].
* [cite_start]`com.codigo.sunat.client`: Cliente OpenFeign para API externa[cite: 50].
* [cite_start]`com.codigo.sunat.dto`: Uso de **records** para transferencia de datos[cite: 51, 99].
* [cite_start]`com.codigo.sunat.entity`: Entidades JPA (`Company`, `Consulta`)[cite: 52, 60, 76].
* [cite_start]`com.codigo.sunat.enums`: Enums para estados y condiciones[cite: 53, 90].
* [cite_start]`com.codigo.sunat.exception`: Manejo global de excepciones[cite: 54, 144].
* [cite_start]`com.codigo.sunat.mapper`: Conversión de DTO a Entidad[cite: 55, 152].
* [cite_start]`com.codigo.sunat.repository`: Repositorios Spring Data JPA[cite: 56, 153].
* [cite_start]`com.codigo.sunat.service`: Lógica de negocio y coordinación[cite: 57, 151].

## [cite_start]4. Funcionalidades de Bonus Implementadas [cite: 158-161]
* [cite_start]**Cache Simple**: Si un RUC fue consultado en los últimos 10 minutos, el sistema retorna los datos de la BD y registra la consulta como `SUCCESS CACHE` sin llamar al proveedor[cite: 160].
* [cite_start]**Manejo de Status 401 y 5xx**: Se implementaron mensajes descriptivos para errores de autenticación y caídas del servidor externo[cite: 161].

## [cite_start]5. Evidencias de Prueba (Scripts curl) [cite: 167-171]

### [cite_start]Caso A: Consulta Exitosa (SUCCESS) [cite: 170]
```bash
curl -X GET http://localhost:8080/api/sunat/ruc/20601030013
