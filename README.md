#  PlayPen Bank - Sistema de Cajero Automático

░░▄▀░░▄▀░█▀█░█░░░█▀█░█░█░█▀█░█▀▀░█▀█░░░█▀▄░█▀█░█▀█░█░█░▀▄░░▀▄░
░▀▄░░▀▄░░█▀▀░█░░░█▀█░░█░░█▀▀░█▀▀░█░█░░░█▀▄░█▀█░█░█░█▀▄░░▄▀░░▄▀
░░░▀░░░▀░▀░░░▀▀▀░▀░▀░░▀░░▀░░░▀▀▀░▀░▀░░░▀▀░░▀░▀░▀░▀░▀░▀░▀░░░▀░░

## Descripción

Sistema de gestión de cajero automático desarrollado como parte del examen parcial de **Programación Avanzada** 

El sistema permite:
- **Registro y autenticación** de clientes y empleados
- **Operaciones de cajero automático** (depósitos, retiros, consulta de saldo)
- **Gestión de transacciones** con registro completo para empleados
- **Gestión de transacciones** con registro propio para cliente
- **Control de saldo "físico"** del cajero automático 
- **Estadísticas de uso** para empleados
- **Interfaz por consola**

---

## Arquitectura del Proyecto

- El proyecto está estructurado en varias capas siguiendo el patrón de diseño DAO (Data Access Object) y DTO (Data Transfer Object)
- El punto de entrada es la clase `Main.java`, que inicializa el sistema y permite a los usuarios interactuar con el cajero automático a través de una interfaz por consola.
- La lógica de negocio se encuentra en los servicios, que interactúan con los DAOs para acceder a la base de datos.
- Los DAOs se encargan de las operaciones CRUD sobre las entidades del sistema, como usuarios, cuentas y transacciones.
- El cajero automático es representado por la clase `PlaypenATM`, que actúa como un singleton para garantizar que solo haya una instancia activa en todo momento.
- Las entidades del sistema están representadas por clases DTO, que facilitan la transferencia de datos entre las capas de la aplicación.
- La interfaz de usuario se maneja a través de clases específicas para el login, registro y menús de cliente y empleado.
- Exceptiones personalizadas se utilizan para manejar errores específicos del sistema, como saldo insuficiente o cuenta bloqueada.
- El sistema utiliza una base de datos MySQL para almacenar la información de usuarios, cuentas y transacciones.

## DTOs

- **UserDTO**: Representa un usuario con sus datos básicos.
- **AccountDTO**: Representa una cuenta bancaria con su saldo y estado.
- **TransactionDTO**: Representa una transacción con detalles de tipo, monto y fecha.
- **ATMStatsDTO**: Representa estadísticas del cajero automático, como saldo total y número de transacciones.

## DAOs

- **UserDAO**: Interfaz para operaciones CRUD de usuarios.
- **AccountDAO**: Interfaz para operaciones CRUD de cuentas.
- **TransactionDAO**: Interfaz para operaciones CRUD de transacciones.
- **ATMDAO**: Interfaz para operaciones CRUD del cajero automático.

## DAOs Implementados

- **UserDAOImpl**: Implementación de UserDAO con métodos para registrar, autenticar y consultar usuarios.
- **AccountDAOImpl**: Implementación de AccountDAO con métodos para crear, actualizar y consultar cuentas.
- **TransactionDAOImpl**: Implementación de TransactionDAO con métodos para registrar transacciones y consultar movimientos.
- **ATMDAOImpl**: Implementación de ATMDAO para gestionar el cajero automático, incluyendo saldo y estadísticas.

## Services

- **UserService**: Lógica de negocio para registrar y autenticar usuarios.
- **ClientService**: Lógica de negocio para operaciones de clientes (depósitos, retiros, transferencias).
- **EmployeeService**: Lógica de negocio para operaciones de empleados (gestión de transacciones, estadísticas).
- **AccountService**: Lógica de negocio para gestionar cuentas bancarias.
- **TransactionService**: Lógica de negocio para registrar y consultar transacciones.

## ATM

- **PlaypenATM**: Clase singleton que representa el cajero automático, gestiona el saldo y las transacciones realizadas. Permite operaciones de depósito y retiro, y mantiene un registro de las transacciones.
- Es el único punto de acceso para interactuar con el cajero automático, garantizando que solo una instancia esté activa en todo momento.

## UI 

- **LoginUI**: Interfaz para el inicio de sesión de usuarios.
- **RegisterUI**: Interfaz para el registro de nuevos clientes.
- **ClientMenu**: Menú principal para clientes, donde pueden realizar operaciones bancarias.
- **EmployeeMenu**: Menú principal para empleados, donde pueden gestionar transacciones y estadísticas.

---


##  Tecnologías Utilizadas

- **Java 24** - Lenguaje principal
- **MySQL**   - Base de datos
- **JDBC**    - Conexión a base de datos
- **Maven**   - Gestión de dependencias
- **JUnit 5** - Testing unitario

---

## Requisitos del Sistema

- **Java JDK 24** o superior
- **MySQL Server** 8.0 o superior
- **Maven** (incluido en IntelliJ IDEA o IDE)

---

## Instalación y Configuración

### 1. Clonar el Repositorio o descargar el ZIP

### 2. Configurar la Base de Datos

1. **Crear la base de datos en MySQL local:**
   ```sql
   CREATE DATABASE playpendb;
   ```

2. **Configurar las credenciales**:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/playpendb";
   private static final String USER = "tu_usuario";
   private static final String PASSWORD = "tu_contraseña";
   ```

### 3. Ejecutar el Proyecto

1. **Abrir el proyecto en IntelliJ IDEA**
2. **Esperar a que Maven descargue las dependencias** -- No olvidar actualizar las dependencies
3. **Ejecutar la clase `Main.java`**

El sistema se inicializará automáticamente creando las tablas necesarias e insertando datos de prueba.
SetupDatabase:
````java
initialize()
    // - Crea las tablas `users`, `accounts`, `transactions`, `atm`
    // - Inserta un cajero automático con saldo inicial
    // - Inserta un empleado por defecto
    // - Inserta clientes de prueba
    // - Inserta cuentas de prueba
    // - Inserta transacciones de prueba
````

---

Usuarios de Prueba

El sistema incluye usuarios predefinidos para testing:

### Empleados

- **DNI:** 11111111 | **Contraseña:** 1234 | **Nombre:** Elliot Alderson

### Clientes

- **DNI:** 22222222 | **Contraseña:** 1234 | **Nombre:** Saul Goodman   
- **DNI:** 33333333 | **Contraseña:** 1234 | **Nombre:** Gordon Freeman  

---

## Funcionalidades

### Para Clientes
- ✅ **Registro** de nuevas cuentas
- ✅ **Login** seguro
- ✅ **Consulta de saldo** y movimientos
- ✅ **Depósitos** de efectivo
- ✅ **Retiros** (verificando saldo de cuenta y cajero)
- ✅ **Transferencias** entre cuentas

### Para Empleados
- ✅ **Login** con credenciales especiales
- ✅ **Visualización** de todas las transacciones
- ✅ **Reposición** de dinero en el cajero
- ✅ **Estadísticas** de uso diario
- ✅ **Gestión** de cuentas bloqueadas

---

## Estructura de la Base de Datos

### Tabla `users`
```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(20) NOT NULL,
    password CHAR(4) NOT NULL,
    name VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    userType VARCHAR(20) NOT NULL
);
```

### Tabla `accounts`
```sql
CREATE TABLE accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    accountNumber VARCHAR(20) NOT NULL,
    balance DOUBLE NOT NULL,
    clientId INT NOT NULL,
    status ENUM('active','inactive','blocked') NOT NULL
);
```

### Tabla `transactions`
```sql
CREATE TABLE transactions (
    tid INT AUTO_INCREMENT PRIMARY KEY,
    accountId INT NOT NULL,
    clientId INT NOT NULL,
    type VARCHAR(20) NOT NULL,
    amount DOUBLE NOT NULL,
    timestamp VARCHAR(30) NOT NULL
);
```

### Tabla `atm`
```sql
CREATE TABLE atm (
    id INT PRIMARY KEY,
    balance DOUBLE NOT NULL
);
```

## Patrones de Diseño Implementados

- **Singleton:** Para la conexión a base de datos y el ATM
- **DAO (Data Access Object):** Para el acceso a datos
- **DTO (Data Transfer Object):** Para la transferencia de datos entre capas

---

### Muchas gracias por el tiempo invertido en revisar este proyecto!

--
--
--
--
--
--


```java
// El empleado tiene una funcionalidad secreta introduciendo 2001 en el menú!
```