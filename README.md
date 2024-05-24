AdvancedToDoAPI

This is a Spring Boot application for managing to-do tasks. It allows users to create, view, modify, and delete
categories and tasks. The application also supports sorting tasks and tracking task history.

Features:

    Category Management
        Create new categories
        View all categories
        Modify existing categories
        Delete categories
    Task Management
        Create new tasks with titles, descriptions, due dates, priorities, and categories
        View all tasks
        Get a specific task by ID
        Modify existing tasks
        Delete tasks
        Move tasks to the next or previous step in their workflow
        Sort tasks by status or due date
    Task History
        Track changes made to tasks

Technologies Used:

    Spring Boot (including Spring MVC, Spring Data JPA)
    JPA (Hibernate)
    Lombok
    ModelMapper
    Log4j (for logging)
    H2 (in-memory database for development and testing)
    Springdoc OpenAPI (for API documentation with Swagger UI)
    Spring Boot Devtools (for improved development experience)
    Unit and Integration Testing with JUnit, Mockito, AssertJ, and Hamcrest

Clone the Repository:

    Use Git to clone this repository to your local machine.

Install Prerequisites:

    Ensure you have Java 17 (or later) and Maven installed on your system.

Build the Project:

    Open a terminal or command prompt and navigate to the project directory.
    Run the command mvn clean install to build the project.

Configure Database Connection:

    Edit the application.properties file located in src/main/resources.
    Update the database connection details according to your database configuration.

Run the Application:

    In your terminal, run the command mvn spring-boot:run to start the application.

API Documentation:

The API is documented using Spring REST Docs. You can view the API documentation
at http://localhost:8080/swagger-ui/index.html.

Testing:

The project includes unit and integration tests using JUnit and Mockito. You can run the tests using mvn test.

I've removed redundant information and grouped related technologies (e.g., Spring Boot includes Spring MVC and Spring
Data JPA).