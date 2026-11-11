# Control System - Control de Ingreso por Código

Sistema de control de ingreso de empleados mediante código único.
Al ingresar el código se crea una sesión de trabajo (Check-In) y al salir se cierra (Check-Out) calculando el tiempo total trabajado.

## Tecnologías

- **Backend:** Java 17 + Spring Boot + Spring Data JPA
- **Frontend:** Angular (en construcción)
- **Base de Datos:** PostgreSQL 16
- **Herramienta BD:** pgAdmin 4

## Estructura del Proyecto
control_system_backend/
├── src/main/java/com/indesap/
│   ├── controller/
│   │   └── AuthController.java   # Login (Check-In) y Logout (Check-Out)
│   ├── entity/
│   │   ├── Employee.java         # code, name
│   │   ├── Session.java          # sessionId, employee, checkInTime, checkOutTime, totalSeconds, status
│   │   └── StatusEmployee.java   # ACTIVE, INACTIVE
│   ├── repository/
│   │   ├── EmployeeRepository.java
│   │   └── SessionRepository.java
│   └── ControlSystemBackendApplication.javajavascript
## Modelo de Datos

### Tabla `employees`
| Campo | Tipo | Descripción |
| --- | --- | --- |
| id | bigint | PK |
| code | varchar | Código único del empleado (ej: E001) |
| name | varchar | Nombre completo |

### Tabla `sessions`
| Campo | Tipo | Descripción |
| --- | --- | --- |
| session_id | bigint | PK |
| employee_id | bigint | FK a employees |
| check_in_time | timestamp | Hora de entrada |
| check_out_time | timestamp | Hora de salida (null si está activo) |
| total_seconds | bigint | Tiempo total de la sesión |
| status | varchar | ACTIVE / INACTIVE |

## Endpoints Implementados

### 1. Login - Check-In
**POST** `/api/auth/login`

**Request:**
```json
{
  "code": "E001"
}
```
**Response 200:**
```json{
  "sessionId": 2,
  "code": "E001",
  "name": "Ayde Natalia Restrepo Vizcaya",
  "checkInTime": "2026-11-10T19:24:39",
  "status": "ACTIVE"
}Response 401: Si el código no existe.json{ "message": "Código no encontrado" }
```
### 2. Logout - Check-Out
**POST** `/api/auth
**Request:**
```json
{
  "sessionId": 2
}
```
**Response 200:**
```
json{
  "message": "Sesión cerrada",
  "totalSeconds": 1473
}
```

### Cómo correr el proyecto
1. Base de Datos
Base de datos: control_system_db en PostgreSQL.
Crear empleados de prueba:sqlINSERT INTO employees (code, name) VALUES
('E001', 'Ayde Natalia Restrepo Vizcaya'),
('E002', 'Juan Sebastian Garcia Castaño'),
('E003', 'Johan Alexis Lopez Cardona');2. Backendbashcd control_system_backend
./gradlew bootRunCorre en http://localhost:8080
3. Probar con curlbash# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"code":"E001"}'

# Logout
curl -X POST http://localhost:8080/api/auth/logout \
  -H "Content-Type: application/json" \
  -d '{"sessionId":2}'Estado Actual - Entrega
Entidades Employee, Session, StatusEmployeeRepositorios con búsqueda por códigoAuthController con login y logoutPersistencia en PostgreSQL verificadaCálculo automático de totalSecondsFrontend Angular - Login por código (en construcción)Listado de sesiones activas en frontendReporte de horas por empleadoFlujo del Sistema
Empleado digita su código (ej: E001)Backend busca el código en employeesSi existe, crea una Session con status=ACTIVE y checkInTime=now()Al salir, se llama a logout con el sessionIdBackend pone checkOutTime=now(), calcula totalSeconds y cambia a INACTIVEAutores
Ayde Natalia Restrepo Vizcaya