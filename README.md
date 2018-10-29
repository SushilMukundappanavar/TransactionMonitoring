# TransactionMonitoring
Webservice API for Transaction monitoring
This service provides the statistics for transaction for last 60 secs. 

# Prerequisites:
The project requires Java 8 and Maven to build.

# Asumptions
The time of transaction is stores in a long veriable so the client application should convert the UTC time to Epoch Milliseconds

# Running the Application
 . mvn spring-boot:run
 
 . Java Standalone Application
 
 . mvn clean package 
 
 . java -jar target/statistics-0.0.1-SNAPSHOT.jar
