# Test project

## Dependencies, enviroment
* Spring boot
* Spring Data JPA
* Spring Web
* Spring Session
* Spring Web
* Spring Security
* Thymeleaf

Java JDK 11 is required to run this project

## How to run it
#### Select the config
Select DB profile in application.properties file. 

`spring.profiles.active=db-h2` || `spring.profiles.active=db-postgre`

If you choose the postgre setting then the application will use a PostgreSQL database instance which runs in the
Azure cloud.

If you choose h2 setting, then the application will use an in-memory DB instance, which is automatically populated 
during the startup process. Watch for this message:

`2020-06-02 23:17:44.253   h.r.testProject.config.H2DbConfig        : Test data loaded into DB!`

#### Start the application
Navigate to the root directory of the project. Run with this command:

`./mvnw spring-boot:run`

After startup the applictaion will be available at `localhost:3000`

## Project specific informations
