# Práctica básica con Microservicios Reactivos

---

### Curso: Análisis y Diseños de Sistemas II

Al descargar el proyecto iniciar cada uno con tu IDE de preferencia
Microservicio de usuarios: Puerto 8080
Microservicio de cuentas: Puerto 8081
Microservicio de trazabilidad: Puerto 8082

Luego abrir un cliente web de prueba (POSTMAN o INSOMNIA) y empieza a realizar las peticiones:

##### Peticiones para el MS de usuarios:

###### Petición: ms-usuarios Obtener Todos los Usuarios

###### url: http://localhost:8080/get/users

###### metodo: GET

```text
No tiene cuerpo
```

###### Petición: ms-usuarios Agregar Nuevo Usuario

###### url: http://localhost:8080/post/create-user

###### metodo: POST

```JSON
{
	"id": "23454645",
	"firstName": "Paula",
	"lastName": "Andrea Bedoya",
	"age": 23,
	"contactDataDto": {
		"address": "clle 20 # 10-35",
		"email": "paula-andrea124@gmail.com", // Must be unique for each user
		"phoneNumber": "3233454645"
	}
}
```

###### Petición: ms-usuarios Obtener Usuario por Id

###### url: http://localhost:8080/get/user/23343554646

###### metodo: GET

```text
No tiene cuerpo
```

###### Petición: ms-usuarios Actualizar Usuario

###### url: http://localhost:8080/update/user

###### metodo: PUT

```JSON
{
	"id": "35645654", // User Id must already exist
	"firstName": "Maria Carolina",
	"lastName": "Minota Ochoa",
	"age": 22,
	"contactDataDto": {
		"address": "clle 20 # 30-30",
		"email": "camila.minota@gmail.com",
		"phoneNumber": "3201235345"
	}
}
```

###### Petición: ms-usuarios Eliminar Usuario por Id

###### url: http://localhost:8080/delete/{userId}

###### metodo: DELETE

```text
No tiene cuerpo
```

---

#### Peticiones para el MS de cuentas y transacciones:

###### Petición: ms-transacciones Obtener Todas las Cuentas

###### url: http://localhost:8081/get/accounts

###### metodo: GET

```text
No tiene cuerpo
```

###### Petición: ms-transacciones Obtener Cuentas por Id del Usuario

###### url: http://localhost:8081/get/accounts-user/{userId}

###### metodo: GET

```text
No tiene cuerpo
```

###### Petición: ms-transacciones Obtener Cuenta por Id

###### url: http://localhost:8081/get/account/{accountId}

###### metodo: GET

```text
No tiene cuerpo
```

###### Petición: ms-transacciones Registar Nueva Transaccion

###### url: http://localhost:8081/post/transaction

###### metodo: POST

```JSON
{
	"id":"686756756",
	"type":"Deposit",
	"userId":"122343545",
	"accountId":"123",
	"value":10000,
	"date":"2022-05-01"
}
```

###### Petición: ms-transacciones Crear Cuenta Bancaria

###### url: http://localhost:8081/post/create-account

###### metodo: POST

```JSON
{
	"accountId":"4356464",
	"userId":"23423453",
	"currentValue":100000, // Initial value for the account
	"lastModificationDate":"2022-25-01" // Current date
}
```

###### Petición: mms-transacciones Eliminar Cuenta por Id

###### url: http://localhost:8081/delete/account/{accountId}

###### metodo: DELETE

```text
No tiene cuerpo
```

---

Por: Juan Camilo Cardona Calderón
Curso de Análisis y Diseño de Sistemas II - Universidad de Antioquia
