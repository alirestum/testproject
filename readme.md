# Test project

## Dependencies, enviroment
* Spring Boot
* Spring Data JPA
* Spring Web
* Spring Session
* Spring Security
* Thymeleaf
* Maven

Java JDK 11 is required to run this project

## How to run it
#### Select the config
Select DB profile in **application.properties** file. 

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

##### User entities
As the task specification requested I created 4 user entities with the specific usernames and roles.
All the users have the same passwords which is `12345`

##### Role types
As the specification requested I created 3 different roles:
* LoggedInUser
* Administrator
* ContentManager

##### Security
Except the _registration_, _login_, and routes to the static files, all other routes require authentication.

##### Captcha
I opted for Google reCaptcha. This solution requires to specify the url on which the webpage with the captcha appears.
Because this project runs locally I specified `localhost` for the url. Because of this the captcha only works if you
run the application locally and access it through the `localhost` url. Google reCaptcha requires both server and client
side validation, both of which are implemented in my solution

##### Registration page
Although the specification did not ask for a registration endpoint, I created one. This page is available at `localhost:3000/user/register`
This registratition page creates a user entity with the _loggedInUser_ role.


