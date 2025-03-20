# Usamos una imagen base de Ubuntu para la fase de construcción
FROM ubuntu:latest AS build

# Instalamos las dependencias necesarias
RUN apt-get update && apt-get install -y \
    openjdk-24-jdk \
    maven \
    && rm -rf /var/lib/apt/lists/*

# Copiamos el código fuente del proyecto al contenedor
COPY . /app

# Establecemos el directorio de trabajo
WORKDIR /app

# Ejecutamos el comando de Maven para construir el proyecto
RUN mvn clean package -DskipTests

# Usamos una imagen base más ligera para la fase de ejecución
FROM openjdk:24-jdk-slim

# Exponemos el puerto en el que correrá la aplicación
EXPOSE 8080

# Copiamos el archivo JAR generado en la fase de construcción
COPY --from=build /app/target/LolApi-0.0.1-SNAPSHOT.jar /app.jar

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
