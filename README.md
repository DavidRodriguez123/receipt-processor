# receipt-processor

Receipt Processor API
This is a Spring Boot-based REST API that processes receipts and assigns points based on the contents of a receipt.

🚀 Features
- Submit a receipt for processing (POST /receipts/process)
- Retrieve the points awarded for a receipt (GET /receipts/{id}/points)
- OpenAPI documentation with Swagger UI
- Built with Spring Boot 3.4.2, Java 17, and Maven

📌 Prerequisites:
Before running the project, make sure you have the following installed:
✅ **Java 17**
✅ **Maven**

🔧 Installation:
Clone the Repository
git clone https://github.com/YOUR-USERNAME/receipt-processor.git
cd receipt-processor

🚀 Running the Application:
Build the Project
Run the following command to download dependencies and compile the code:
**mvn clean install**
 
Run the Application
Start the Spring Boot application using:
**mvn spring-boot:run**

The API is now running at:
**http://localhost:8080**

📌 API Endpoints:
📍 1. Submit a Receipt (POST /receipts/process)
**http://localhost:8080/receipts/process**
📍 2. Get Points for a Receipt (GET /receipts/{id}/points)
**http://localhost:8080/receipts/{id}/points"**

📖 API Documentation:
This project includes Swagger UI for easy API testing.
Once the app is running, open Swagger UI in your browser:
**http://localhost:8080/swagger-ui/index.html**

🛠️ Building & Running as a JAR:
To build and package your Spring Boot application into a JAR file, run:
mvn clean package
java -jar target/receipt-processor-0.0.1-SNAPSHOT.jar

🎉 Now Your Spring Boot Project is Ready to Use!
Let me know if you need any refinements! 🚀😊
