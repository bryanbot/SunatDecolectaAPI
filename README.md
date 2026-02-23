# SunatDecolectaAPI
En la presente REPO se ha construido un microservicio para consumir la API de consulta de información de Sunat brindada por Decolecta
# Microservicio Sunat-Consulta (Examen G14)

Este microservicio permite la consulta de RUCs a través de la API externa de **Decolecta**, integrando persistencia en base de datos, manejo de errores avanzado con OpenFeign y una estrategia de cache local.

## 1. Requisitos Previos
* **Java**: Versión 17.
* **Spring Boot**: Versión 4.0.3.
* **Base de Datos**: PostgreSQL (Base de datos: `bdjava14`).
* **Maven**: Para la gestión de dependencias.

## 2. Configuración de Variables de Entorno
El sistema requiere un token de seguridad para comunicarse con la API de Decolecta. Para modificar el valor de este token, una vez copiado el proyecto, dirigirse al archivo **application.properties** que se encuentra en `src/main/resources`.  

Configure la siguiente variable presente en el archivo:
* **Variable**: `decoleta.token`
* **Valor sugerido**: `sk_<generateyourown>`.  

Una vez configurado el token de **Decolecta**, es importante revisar la `url`, `username` y `password` de la base de datos. Estas variables fueron definidas en el archivo **application.properties** mencionado anteriormente.

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
```
#### Respuesta
```json
{
    "condicion": "HABIDO",
    "createdAt": "2026-02-23T17:57:05.8894064",
    "departamento": "LIMA",
    "direccion": "CAL. RICARDO ANGULO RAMIREZ NRO 745 DEP. 202 URB. CORPAC ",
    "distrito": "SAN ISIDRO",
    "esAgenteRetencion": false,
    "esBuenContribuyente": false,
    "estado": "ACTIVO",
    "id": 1,
    "provincia": "LIMA",
    "razonSocial": "REXTIE S.A.C.",
    "ruc": "20601030013",
    "ubigeo": "150131"
}
```

### Caso B: Validación de RUC (11 dígitos)
```bash
curl -X GET http://localhost:8080/api/sunat/ruc/12345
```
#### Respuesta
```json
{
    "message": "RUC debe tener 11 dígitos"
}
```

### Caso C: Error de Proveedor (RUC no válido)
```bash
curl -X GET http://localhost:8080/api/sunat/ruc/11111111111
```
#### Respuesta
```json
{
    "message": "{\"message\":\"ruc no valido\"}"
}
```
### Caso D: Ver Historial de Consultas
```bash
curl -X GET http://localhost:8080/api/sunat/ruc/20601030013/consultas
```
#### Respuesta
```json
[
    {
        "rucConsultado": "20601030013",
        "resultado": "SUCCESS",
        "company": {
            "condicion": "HABIDO",
            "createdAt": "2026-02-23T17:00:02.560208",
            "departamento": "LIMA",
            "direccion": "CAL. RICARDO ANGULO RAMIREZ NRO 745 DEP. 202 URB. CORPAC ",
            "distrito": "SAN ISIDRO",
            "esAgenteRetencion": false,
            "esBuenContribuyente": false,
            "estado": "ACTIVO",
            "hibernateLazyInitializer": {},
            "id": 1,
            "provincia": "LIMA",
            "razonSocial": "REXTIE S.A.C.",
            "ruc": "20601030013",
            "ubigeo": "150131"
        },
        "createdAt": "2026-02-23T17:57:05.905408",
        "mensajeError": null,
        "providerStatusCode": 200,
        "id": 4
    },
    {
        "rucConsultado": "20601030013",
        "resultado": "SUCCESS",
        "company": {
            "condicion": "HABIDO",
            "createdAt": "2026-02-23T17:00:02.560208",
            "departamento": "LIMA",
            "direccion": "CAL. RICARDO ANGULO RAMIREZ NRO 745 DEP. 202 URB. CORPAC ",
            "distrito": "SAN ISIDRO",
            "esAgenteRetencion": false,
            "esBuenContribuyente": false,
            "estado": "ACTIVO",
            "hibernateLazyInitializer": {},
            "id": 1,
            "provincia": "LIMA",
            "razonSocial": "REXTIE S.A.C.",
            "ruc": "20601030013",
            "ubigeo": "150131"
        },
        "createdAt": "2026-02-23T17:02:52.409012",
        "mensajeError": "SUCCESS CACHE",
        "providerStatusCode": 200,
        "id": 3
    },
    {
        "rucConsultado": "20601030013",
        "resultado": "SUCCESS",
        "company": {
            "condicion": "HABIDO",
            "createdAt": "2026-02-23T17:00:02.560208",
            "departamento": "LIMA",
            "direccion": "CAL. RICARDO ANGULO RAMIREZ NRO 745 DEP. 202 URB. CORPAC ",
            "distrito": "SAN ISIDRO",
            "esAgenteRetencion": false,
            "esBuenContribuyente": false,
            "estado": "ACTIVO",
            "hibernateLazyInitializer": {},
            "id": 1,
            "provincia": "LIMA",
            "razonSocial": "REXTIE S.A.C.",
            "ruc": "20601030013",
            "ubigeo": "150131"
        },
        "createdAt": "2026-02-23T17:00:39.088232",
        "mensajeError": "SUCCESS CACHE",
        "providerStatusCode": 200,
        "id": 2
    },
    {
        "rucConsultado": "20601030013",
        "resultado": "SUCCESS",
        "company": {
            "condicion": "HABIDO",
            "createdAt": "2026-02-23T17:00:02.560208",
            "departamento": "LIMA",
            "direccion": "CAL. RICARDO ANGULO RAMIREZ NRO 745 DEP. 202 URB. CORPAC ",
            "distrito": "SAN ISIDRO",
            "esAgenteRetencion": false,
            "esBuenContribuyente": false,
            "estado": "ACTIVO",
            "hibernateLazyInitializer": {},
            "id": 1,
            "provincia": "LIMA",
            "razonSocial": "REXTIE S.A.C.",
            "ruc": "20601030013",
            "ubigeo": "150131"
        },
        "createdAt": "2026-02-23T17:00:02.595209",
        "mensajeError": null,
        "providerStatusCode": 200,
        "id": 1
    }
]
```
## 5. Revisión de la base de datos (SQL commands)
Una vez ejecutada la API, es posible revisar la informacion almacenada en la base de datos. Si deseas verificar los datos directamente en PostgreSQL, puedes utilizar los siguientes comandos:  
``` sql
SELECT * FROM companies;
```
``` sql
SELECT * FROM consultas ORDER BY created_at DESC;
```
