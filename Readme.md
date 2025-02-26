# 📦 Aplicación de Gestión de Inventario

## 🚀 Descripción
Esta es una aplicación web que permite gestionar un inventario de productos mediante backend en **Spring Boot**. La aplicación incluye:

- **CRUD de productos**: Crear, leer, actualizar y eliminar productos.
- **Validaciones** en frontend y backend.
- **Swagger UI** para documentación de API.
- **Logging** con Logback.
- **Base de datos en PostgreSQL** usando Docker.
- **Pruebas unitarias** con JUnit y Jest.

---
## 🛠️ Tecnologías Usadas
- **Backend**: Spring Boot 3.4.3, Java 17, JPA, PostgreSQL, Swagger.
- **Base de Datos**: PostgreSQL en Docker.
- **Herramientas de CI/CD**: Gradle, Docker.

---
## 🖥️ Instalación y Ejecución

### 🔧 **Requisitos Previos**
- Tener **Docker** y **Docker Compose** instalados.
- Tener **Java 17** y **Node.js** instalado.

### 🐳 **Ejecutar con Docker**

1. Clonar el repositorio:
   ```sh
   git clone https://github.com/tu-usuario/inventory-app.git
   cd inventory-app
   ```
2. Construir la imagen de la aplicación backend:
   ```sh
   ./gradlew build -x test
   ```
3. Levantar la aplicación con Docker Compose:
   ```sh
   docker-compose up -d
   ```
4. La API estará disponible en: `http://localhost:8080`
5. El frontend estará en: `http://localhost:4200`

---
## 📜 API REST - Endpoints
La API proporciona los siguientes endpoints:

### 🔹 **Productos**
| Método | Endpoint            | Descripción                        |
|--------|---------------------|------------------------------------|
| `GET`  | `/api/v1/products`  | Listar todos los productos        |
| `POST` | `/api/v1/products`  | Crear un nuevo producto           |
| `GET`  | `/api/v1/products/{id}` | Obtener un producto por ID   |
| `PUT`  | `/api/v1/products/{id}` | Actualizar un producto      |
| `DELETE` | `/api/v1/products/{id}` | Eliminar un producto      |

### 🔹 **Formato de respuesta estándar**
Todas las respuestas siguen el siguiente formato:
```json
{
  "status": "success", // o "error"
  "message": "Operación realizada exitosamente",
  "data": {...} // Objeto con los datos devueltos
}
```

### 🔹 **Ejemplo de petición en Postman**
#### **Crear un producto**
- **URL:** `POST http://localhost:8080/api/v1/products`
- **Cuerpo JSON:**
```json
{
  "name": "Laptop",
  "category": "Electrónica",
  "price": 1200.50,
  "stock": 10
}
```

---
## 📄 Swagger UI
La documentación de la API está disponible en **Swagger**:
🔗 **Acceder a Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---
## 🛠️ **Pruebas**
### **Backend: JUnit**
Ejecutar pruebas unitarias en el backend:
```sh
./gradlew test
```

### **Frontend: Jest**
Ejecutar pruebas unitarias en el frontend:
```sh
cd frontend
npm install
npm test
```

---
## 🌐 **Despliegue en AWS**
Para un despliegue en AWS, se recomienda:
- **Backend**: AWS Elastic Beanstalk + RDS (PostgreSQL).
- **Frontend**: AWS S3 + CloudFront.
- **Base de Datos**: Amazon RDS (PostgreSQL).

---
## 📜 **Licencia**
Este proyecto está bajo la licencia MIT.

---
## ✉️ Contacto
Si tienes alguna duda, contáctame en: [danielestebandh@gmail.com](mailto:danielestebandh@gmail.com)

