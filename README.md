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
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```  

## Contribution Guidelines
1. **Fork the Repository**
2. **Create a Feature Branch** (`feature/your-feature`)
3. **Commit Your Changes**
4. **Push the Branch & Create a Pull Request**

## License
This project is licensed under the [MIT License](LICENSE).

## Contact
For any issues or suggestions, feel free to open an issue or contact me at `your-email@example.com`.

---
### 🚀 Happy Coding!

