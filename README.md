# 🚚 Real-Time Fleet Management System

A microservices-based backend system to track real-time location of fleet vehicles (e.g., trucks), built using Java Spring Boot, Kafka, Redis, MongoDB, and AWS-ready architecture.

---

## 📘 Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Microservices Breakdown](#microservices-breakdown)
- [Kafka Topics](#kafka-topics)
- [API Endpoints](#api-endpoints)
- [Setup Instructions](#setup-instructions)
- [Future Enhancements](#future-enhancements)
- [License](#license)

---

## 🧭 Overview

This project simulates a fleet of delivery trucks and tracks their real-time locations using Kafka for streaming, Redis for caching, and MongoDB for historical data. The system also includes admin features to manage trucks and a real-time dashboard to display live movement.

---

## 💻 Tech Stack

| Layer                  | Technology                         |
|------------------------|-------------------------------------|
| Language               | Java 17                             |
| Build Tool             | Maven                               |
| Framework              | Spring Boot                         |
| Messaging              | Kafka                               |
| Caching                | Redis                               |
| Database               | MongoDB / SQL                       |
| Queue (optional alt)   | RabbitMQ / AWS SQS                  |
| REST APIs              | Spring Web                          |
| Microservice Registry  | (Optional) Eureka, Consul           |
| Deployment             | AWS EC2 / Docker / Kubernetes       |
| Documentation          | Swagger / OpenAPI                   |

---

## 📐 Architecture

Architecture Diagram



                               +----------------+
                               |   Admin Panel  |
                               | (Postman / UI) |
                               +--------+-------+
                                        |
                      CRUD Truck Info   |  (REST API)
                                        v
                            +-----------+------------+
                            |       Admin Service     |
                            | (Spring Boot + MongoDB) |
                            +-----------+------------+
                                        |
                                        | Truck Info Stored
                                        v
                                  +-----+------+
                                  |  MongoDB   |
                                  +------------+

--------------------------------------------------------------------------------

                     Simulate Location                Consume & Store
                  +--------------------+           +--------------------+
                  | Location Simulator |           | Location Ingestor  |
                  | (Kafka Producer)   |---------> | (Kafka Consumer)   |
                  +--------------------+           +--------------------+
                           |                                |
                           | GPS Data                       | Save latest location
                           v                                v
                     +----------------+             +---------------+
                     | Kafka Topic:   |             |   Redis       |
                     | truck-location |             | (Cache Layer) |
                     +----------------+             +---------------+
                                                        |
                                                Retrieve location
                                                        v
                                                +---------------+
                                                |   Frontend /  |
                                                |   Customer UI |
                                                +---------------+








---

## 🧩 Microservices Breakdown

### 1. **Admin Service**
- Register, update, delete, and fetch truck information.
- Stores metadata in MongoDB/SQL.

### 2. **Location Simulator (Producer)**
- Simulates truck movement by sending GPS coordinates every few seconds to Kafka.

### 3. **Location Ingestor Service (Consumer)**
- Consumes messages from Kafka.
- Stores current truck location in Redis (for dashboard).
- Optionally stores historical data in DB.

### 4. **Frontend (Dashboard)**
- Fetches live and historical location data.
- Calls Admin APIs to manage trucks.

---

## 🧵 Kafka Topics

| Topic Name          | Description                          |
|---------------------|--------------------------------------|
| `truck-location`     | Real-time truck GPS data (JSON)      |

---

## 📡 API Endpoints

### Admin Service

| Method | Endpoint              | Description                      |
|--------|-----------------------|----------------------------------|
| POST   | `/admin/truck`        | Register new truck               |
| GET    | `/admin/trucks`       | List all trucks                  |
| GET    | `/admin/truck/{id}`   | Get truck details                |
| PUT    | `/admin/truck/{id}`   | Update truck info                |
| DELETE | `/admin/truck/{id}`   | Delete a truck                   |

### Location Ingestor Service

| Method | Endpoint                       | Description                     |
|--------|--------------------------------|---------------------------------|
| GET    | `/truck/{id}/location`         | Get real-time location          |
| GET    | `/truck/{id}/historical`       | Get historical location trail   |

---



## 🗃️ Data base Management System

✅ SQL for Truck & Driver Metadata
We use a relational database (such as PostgreSQL or MySQL) to store static and structured metadata about trucks and drivers.

📌 Reasons:
Data Integrity: SQL databases enforce strict schema rules, data types, and constraints (e.g., foreign keys), ensuring data consistency across truck and driver records.

ACID Compliance: Guarantees reliable transactions, crucial when updating or deleting critical fleet data.

Complex Queries: Easier to perform joins, filters, and aggregations (e.g., all active trucks assigned to a driver).

Normalized Structure: Tables can be normalized (e.g., separating trucks and drivers), improving maintainability and performance.

Example: A truck’s model, capacity, registration number, and driver details are relatively static and benefit from strict schema enforcement and relational modeling. Also we can map each truck to it's driver using the foreign and private keys. 

✅ MongoDB for Location History
We use MongoDB, a NoSQL document database, to store the high-frequency GPS trail data generated by trucks.

📌 Reasons:
Flexible Schema: Each truck can have a variable-length array of location data without schema limitations.

GeoSpatial Support: MongoDB natively supports geospatial indexing and queries, like proximity searches and route reconstruction.

Efficient for High Volume Writes: Handles large volumes of location events without needing complex schema migrations.

Document-Oriented Modeling: A single document can represent a truck’s full movement history, enabling fast read/write access.

Example: Every few seconds, a truck sends its latitude, longitude, and timestamp. These events are stored in MongoDB under that truck’s document, allowing efficient geospatial queries and visualizations. We might not save the complete duration of the truck but we save the required and most prioritised one's with help of time stamp, latitude and longitudes of the location so for this we might have so much meta data and it may vary according to the vehicle, location and time so NoSQL is preferred. 




### API ENDPOINTS WITH REQUEST AND RESPONSE MODELS 

## 1. 🚛 Register a new Truck
Method: POST

Endpoint: /admin/truck

Request Body (JSON):

JSON BODY 

{

  "truckNumber": "KA01AB1234",

  "driverName": "John Doe",

  "driverContact": "9876543210",

  "truckType": "12-wheeler",

  "capacity": 10000

}

Model Class: TruckRequestDTO



Response (201 Created):



JSON



{

  "id": 1,

  "message": "Truck registered successfully"

}

Model Class: TruckResponseDTO


## 2. 📋 List all Trucks
Method: GET

Endpoint: /admin/trucks

Response (200 OK):

JSON

[

  {
     "id": 1,

  "truckNumber": "KA01AB1234",

  "driverName": "John Doe",

   "truckType": "12-wheeler",

   "capacity": 10000

  },

  {

  "id": 2,

   "truckNumber": "TN02CD5678",

   "driverName": "Jane Smith",

   "truckType": "6-wheeler",

  "capacity": 5000

  }

]

Model Class: List<TruckResponseDTO>



## 3. 🔍 Get Truck Details by ID

Method: GET



Endpoint: /admin/truck/{id}



Response (200 OK):



JSON



{

  "id": 1,
  
"truckNumber": "KA01AB1234",

  "driverName": "John Doe",

  "truckType": "12-wheeler",

  "capacity": 10000

}

Model Class: TruckResponseDTO



## 4. ✏️ Update Truck Info

Method: PUT



Endpoint: /admin/truck/{id}



Request Body (JSON):





{

  "truckNumber": "KA01AB9999",

  "driverName": "John D",

  "driverContact": "9876540000",

  "truckType": "10-wheeler",

  "capacity": 9500

}

Model Class: TruckRequestDTO



Response (200 OK):










JSON



{

  "id": 1,

  "message": "Truck updated successfully"

}

Model Class: TruckResponseDTO



## 5. ❌ Delete a Truck

Method: DELETE



Endpoint: /admin/truck/{id}



Response (200 OK):


JSON



{

  "message": "Truck deleted successfully"

}


Model Class: GenericResponseDTO (just a wrapper for messages)





## ROUGH - WORK

Final Checklist: Required Installations for Our Project
Tool	Purpose	Installed?
 Java 17	Core backend language	
 Spring Tool Suite (STS)	IDE for Spring Boot	
 Maven	Build tool and dependency manager	
 MongoDB (Docker)	Database for storing truck/admin/location data	✔️ Running via Docker
 Docker	Containerization (Mongo, Redis, future use)	
 Homebrew	macOS package manager	
 mongosh	CLI for MongoDB debugging/inspection	
 Postman	(Optional) For API testing	
 Redis (Later)	In-memory cache for real-time location	
 Kafka (Later)	Streaming system for location updates	
 AWS CLI / SQS (Optional)	Optional, for demonstrating queue integration	




com.fleetmanagement.adminservice

│

├── controller       → For REST controllers (expose endpoints)

├── service          → For business logic

│   └── impl         → Concrete implementations

├── repository       → For DAO layer (interfaces extending JpaRepository)

├── entity           → For JPA Entity classes

├── dto              → (Optional) For request/response payload objects

├── config           → For configuration files (e.g., Swagger, DB, security)

└── exception        → For custom exceptions, handlers




## 🚀 Setup Instructions

### Prerequisites

- Java 17+
- Maven
- Docker (for Redis, Mongo, Kafka)
- Kafka + Zookeeper (local or cloud)
- Redis
- MongoDB




Example data flow of LOCATION INGESTOR SERVICE 

Truck Device (or Simulator)
     ↓
Kafka Producer → [ truck-location topic ]
     ↓
Location Ingestor (Kafka Consumer)
     ↙                        ↘
Redis (live data)     MongoDB (historical points)
     ↓                        ↓
API Response          API Response






### Steps

```bash
# Clone the repo
git clone https://github.com/<your-username>/fleet-management-system.git
cd fleet-management-system

# Start required services
docker-compose up -d  # (optional for Redis, Kafka, MongoDB)

# Build all services
mvn clean install

# Run Admin Service
cd admin-service
mvn spring-boot:run

# Run Location Simulator
cd location-simulator
mvn spring-boot:run

# Run Ingestor Service
cd location-ingestor
mvn spring-boot:run
