# AI-Powered Workout Tracking and Recommendation Platform

A microservices-based backend application built using **Java and Spring Boot** to track workout activities and generate AI-powered fitness recommendations using Google's Gemini API.

## 🚀 Features

- Workout activity tracking and storage.
- AI-powered workout analysis and recommendations.
- Microservices architecture for modularity and scalability.
- Asynchronous communication using RabbitMQ.
- Secure authentication and role-based authorization using Keycloak.
- Service discovery using Netflix Eureka.
- Centralized configuration using Spring Cloud Config Server.

## 🛠️ Tech Stack

- **Backend:** Java, Spring Boot, Spring Cloud
- **Databases:** MySQL, MongoDB
- **Messaging:** RabbitMQ
- **Security:** Keycloak, Spring Security, JWT
- **AI Integration:** Google Gemini API
- **Testing:** Postman
- **Containerization:** Docker

## 🏗️ Architecture

The platform consists of User Service, Activity Service, AI Service, API Gateway, Eureka Server, and Config Server.

RabbitMQ enables asynchronous communication between the Activity Service and AI Service, allowing workout activities to be processed without waiting for AI analysis to finish.

## 🔮 Future Enhancements

- Develop a user-friendly frontend.
- Add workout progress dashboards and personalized recommendations.
- Improve automated testing and deployment.

## 👨‍💻 Author

**Amey Nemmaniwar**  
B.Tech, NIT Warangal

