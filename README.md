# Bike Sharing Application

## Overview
This project is a **Bike Sharing Application** implemented in **Java**. The system allows users to rent and return bikes at various stations, track availability, and manage their accounts. It is designed for urban areas to facilitate easy and eco-friendly transportation.

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
Eclipse Project Setup

This project is designed to be run in Eclipse. In order to run it successfully, you need to manually add the following dependencies to the classpath:

JavaFX JARs for GUI components (if applicable)

JUnit for testing

PostgreSQL JDBC Driver to connect to the database

Additionally, update the VM arguments when running the application:

-Djavafx.verbose=true --module-path "ls/lib" --add-modules javafx.controls,javafx.fxml

This project is designed to be run in Eclipse. In order to run it successfully, you need to manually add the following dependencies:


## License
This project is licensed under the [MIT License](LICENSE).



---
### 🚀 Happy Coding!

