# שלב 1: בניית הפרויקט
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# שלב 2: הרצת האפליקציה (השתמשתי ב-Amazon Corretto )
FROM amazoncorretto:17-alpine
COPY --from=build /target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
