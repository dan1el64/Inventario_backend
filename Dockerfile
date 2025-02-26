## === Primera etapa: Compilación con Gradle ===
#FROM gradle:8.5-jdk21 AS builder
#
## Establecer el directorio de trabajo dentro del contenedor
#WORKDIR /app
#
## Copiar archivos de configuración y código fuente
#COPY . .
#
## Limpiar cachés y construir el proyecto (excluyendo pruebas para acelerar)
#RUN gradle clean build -x test --no-daemon

# === Segunda etapa: Ejecución en OpenJDK ===
FROM openjdk:21-jdk-slim

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar solo el archivo JAR desde la etapa anterior
#COPY --from=builder /app/build/libs/inventario-0.0.1-SNAPSHOT.jar app.jar

COPY /build/libs/inventario-0.0.1-SNAPSHOT.jar app.jar
# Configurar variables de entorno
ENV SPRING_PROFILES_ACTIVE=prod

# Exponer el puerto de la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
