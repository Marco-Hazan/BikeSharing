# Bike Sharing Application

## Overview
This project is a **Bike Sharing Application**  implemented in Java as part of a university course on Software Engineering Best Practices. The primary goal was to apply software design principles and architectural patterns to create a maintainable and scalable application.

This project follows several design patterns, including:

- Data Access Object (DAO) to abstract and manage database interactions.

- Model-View-Controller (MVC) for separating concerns in the graphical user interface.

- Singleton Pattern for managing shared resources efficiently.

- Factory Pattern for creating objects with a consistent structure.

- Observer Pattern to notify components about state changes dynamically.

 The system allows users to rent and return bikes at various stations, track availability, and manage their accounts. It is designed for urban areas to facilitate easy and eco-friendly transportation.

## Project Documentation

This project includes a comprehensive 50-page PDF document that details how the application works. The document contains:

- se Case Diagrams

- User Scenarios

- Class Diagrams

- Flowcharts

- Sequence Diagrams

- Real-life operational guidelines

- Much more

Unfortunately, the document is written in Italian, as nobody specified otherwise during its creation.

## Features
- **User Registration & Authentication**
- **Bike Rental & Return System**
- **Real-time Bike Availability Tracking**
- **Pricing & Payment Integration**
- **Admin Dashboard for Bike & Station Management**

## Technologies Used
- **Java 11** (Core application logic)
- **PostgreSQL** (Relational database)
- **JUnit** (Testing framework)
- **JavaFX** (GUI components, if applicable)
- **JDBC** (Database connection)

## Installation & Setup

### Prerequisites
Ensure you have the following installed:
- Java 11
- PostgreSQL (or any supported database)
- Docker (optional for containerized deployment)

### Steps to Run the Application
1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/bike-sharing.git
   cd bike-sharing
   ```
2. **Configure the Database**
   - Go to src/application/principalclass/UtilFunc.java to configure credentials in order to access the database

3. **Build & Run the Application**
    - #### Eclipse Project Setup

        This project is designed to be run in Eclipse. In order to run it successfully, you need to manually add the following dependencies to the classpath:

        - JavaFX JARs for GUI components (if applicable)

        - JUnit for testing

        - PostgreSQL JDBC Driver to connect to the database

        Additionally, update the VM arguments when running the application:
        ```bash
                -Djavafx.verbose=true --module-path "ls/lib" --add-modules javafx.controls,javafx.fxml
        ```
    - #### Building from Command Line

        To build the project from the command line, follow these steps:

        Navigate to the project directory

        cd bike-sharing

        Compile the application
        ```bash
            javac -cp "lib/*" -d bin src/com/example/*.java
        ```
        Run the application
        ```bash
            java -Djavafx.verbose=true --module-path "ls/lib" --add-modules javafx.controls,javafx.fxml -cp "bin:lib/*" com.example.Main
        ```

## License
This project is licensed under the [MIT License](LICENSE).



---
### 🚀 Happy Coding!

