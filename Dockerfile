# Usa una imagen base de Java
FROM amazoncorretto:18-alpine-jdk

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR de tu aplicación al contenedor
COPY target/StorageManagementSystem-0.0.2-SNAPSHOT.jar app.jar

# Expone el puerto en el que tu aplicación se ejecutará
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
