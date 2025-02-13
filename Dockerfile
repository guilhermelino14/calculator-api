# Etapa de build com Gradle e JDK 17
FROM gradle:8.2.1-jdk17 AS build

WORKDIR /app

# Copiar arquivos do projeto
COPY build.gradle settings.gradle ./
COPY calculator/src ./calculator/src
COPY calculator/build.gradle ./calculator/build.gradle
COPY rest/src ./rest/src
COPY rest/build.gradle ./rest/build.gradle

# Ajustar permissões para o usuário gradle
USER root
RUN chown -R gradle:gradle /app
USER gradle

# Limpar o cache do Gradle manualmente
RUN rm -rf /home/gradle/.gradle/caches

# Executar o build com logs detalhados
RUN gradle --info --stacktrace clean build --no-daemon

# Etapa de execução com OpenJDK 17
FROM eclipse-temurin:17-jre AS run

RUN adduser --system --group app-user

WORKDIR /app
USER app-user

# Copiar o JAR gerado
COPY --from=build /app/rest/build/libs/rest-0.0.1-SNAPSHOT.jar /app/app.jar

# Expor a porta
EXPOSE 8080

# Definir o comando de execução
ENTRYPOINT ["java", "-jar", "/app/app.jar"]