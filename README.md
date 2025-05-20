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

![Architecture Diagram](./A_diagram_of_a_Real-Time_Fleet_Management_System_i.png)

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

## 🚀 Setup Instructions

### Prerequisites

- Java 17+
- Maven
- Docker (for Redis, Mongo, Kafka)
- Kafka + Zookeeper (local or cloud)
- Redis
- MongoDB

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
