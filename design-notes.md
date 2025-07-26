# DESIGN NOTES
Aquí estará el desarrrollo en base a cómo fui pensando el TP.

## IDEA
User es una clase abstracta que representa a un usuario del sistema.
Client y Employee son subclases de User que representan a un cliente y un empleado del banco, respectivamente.
Cliente realiza transacciones, que las transacciones son retiro y depósito en su cuenta.
Employee realiza solamente la acción de ver estadísticas del ATM y cargar dinero al ATM.
El ATM es una clase que representa el cajero automático y tiene un saldo de dinero disponible que se disminuye con las extracciones del cliente y aument con el depósito de dinero por parte del empleado y también por parte del cliente.
Trabajaremos con una base de datos MYSQL ya conectada a través de JDBC, donde se almacenarán los usuarios, cuentas y transacciones.
Las transacciones se guardarán en una tabla de transacciones, donde se registrará el tipo de transacción (depósito o retiro), la cantidad y la fecha. También tendrán un tipo de transacción (depósito o retiro) y una referencia a la cuenta afectada.
Para el flujo, debe haber un menú por consola para el cliente y otro para el empleado. Para ello se debe acceder con un login, donde se ingresa el usuario y la contraseña. Si es correcto, se accede al menú correspondiente.
Todo esto debe estar estructurado en paquetes y clases, siguiendo el patrón de diseño DAO (Data Access Object) con DTO para la interacción con la base de datos.
Todo esto debe estar alojado en nuestra base de datos MYSQL 'playpenbank'. Por lo que debemos generar las tablas necesarias para almacenar los usuarios, cuentas y transacciones.
El employee se creará con un usuario y contraseña por defecto, y el cliente se creará con un usuario y contraseña que se le solicitará al momento de crear la cuenta, por ende, se debe poder crear una cuenta si aún no la tiene y crear una neuva Account de banco para el usuario que se registra.
También debemos generar algunos datos de prueba para poder probar el sistema, como por ejemplo, un cliente con una cuenta y un empleado con un usuario y contraseña por defecto, algunas cuentas y transacciones.
Necesito tu ayuda para guiarme y armar todo.




## ESTRUCTURA GENERAL
- model
- dao
- service
- ui
- util

## ENTIDADES

### User (abstract)
- Int id
- String username
- String password
- String type
- 
- name
- 

### Client (extends User) 
- private id
- username
- 


### Employee (extends User)

### Account

### Transaction

## DAO
- UserDao
- ClientDao
- EmployeeDao
- AccountDao
- TransactionDao

## SERVICE
- UserService
- ClientService
- EmployeeService
- AccountService
- TransactionService

### UserService

### ClientService

### EmployeeService

### AccountService

### TransactionService

## UI
- Login
- ClientUI
- EmployeeUI



## Estructura General del Proyecto
src/
└─ main/
└─ java/
└─ com/
└─ playpenbank/
├─ model/
│    ├─ User.java
│    ├─ Client.java
│    ├─ Employee.java
│    ├─ Account.java
│    ├─ Transaction.java
│    └─ playpenATM.java
├─ dto/
│    ├─ UserDTO.java
│    ├─ AccountDTO.java
│    └─ TransactionDTO.java
├─ dao/
│    ├─ UserDAO.java
│    ├─ AccountDAO.java
│    ├─ TransactionDAO.java
│    └─ impl/
│         ├─ UserDAOImpl.java
│         ├─ AccountDAOImpl.java
│         └─ TransactionDAOImpl.java
├─ service/
│    ├─ UserService.java
│    ├─ ClientService.java
│    ├─ EmployeeService.java
│    ├─ AccountService.java
│    └─ TransactionService.java
└─ ui/
├─ LoginUI.java
├─ ClientMenu.java
└─ EmployeeMenu.java




---

## 🗺️ **Mapa de Procesos**

- **Inicio**
    - Login
    - Registro de cliente

- **Cliente**
    - Ver saldo
    - Ver movimientos
    - Depositar dinero
    - Retirar dinero
    - Transferir a otra cuenta

- **Empleado**
    - Ver todas las transacciones
    - Cargar dinero al ATM
    - Ver estadísticas del ATM

---

## 🔄 **Flujos de cada caso**

### 1. **Login**
1. Usuario ingresa DNI y clave.
2. El sistema valida credenciales.
3. Si es correcto, redirige según tipo de usuario (cliente/empleado).
4. Si es incorrecto, muestra error.

---

### 2. **Registro de cliente**
1. Usuario ingresa datos personales, DNI y clave.
2. El sistema valida formato y unicidad del DNI.
3. Si es válido, crea usuario y cuenta bancaria asociada.
4. Si hay error, muestra mensaje.

---

### 3. **Ver saldo (Cliente)**
1. Cliente selecciona opción "Ver saldo".
2. El sistema busca la cuenta activa del cliente.
3. Muestra el saldo actual.

---

### 4. **Ver movimientos (Cliente)**
1. Cliente selecciona "Ver movimientos".
2. El sistema busca transacciones asociadas al cliente.
3. Muestra la lista de movimientos.

---

### 5. **Depositar dinero (Cliente)**
1. Cliente ingresa monto a depositar.
2. El sistema valida el monto.
3. Si es válido, suma al saldo de la cuenta y al ATM.
4. Registra la transacción.
5. Muestra confirmación o error.

---

### 6. **Retirar dinero (Cliente)**
1. Cliente ingresa monto a retirar.
2. El sistema valida monto, saldo y fondos en el ATM.
3. Si es válido, descuenta de la cuenta y del ATM.
4. Registra la transacción.
5. Muestra confirmación o error.

---

### 7. **Transferir a otra cuenta (Cliente)**
1. Cliente ingresa cuenta destino y monto.
2. El sistema valida datos, saldo y existencia de la cuenta destino.
3. Si es válido, descuenta de la cuenta origen y suma a la destino.
4. Registra ambas transacciones.
5. Muestra confirmación o error.

---

### 8. **Ver todas las transacciones (Empleado)**
1. Empleado selecciona la opción.
2. El sistema muestra todas las transacciones registradas.

---

### 9. **Cargar dinero al ATM (Empleado)**
1. Empleado ingresa monto a cargar.
2. El sistema valida el monto.
3. Suma al saldo del ATM.
4. Muestra confirmación o error.

---

### 10. **Ver estadísticas del ATM (Empleado)**
1. Empleado selecciona la opción.
2. El sistema calcula y muestra estadísticas (total transacciones, tipo más frecuente, totales, etc.).

---
