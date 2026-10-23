# AI-Powered Workout Tracking and Recommendation Platform

A microservices-based backend application built using **Java and Spring Boot** to track workout activities and generate AI-powered fitness recommendations using Google's Gemini API.

<img width="969" height="552" alt="Screenshot 2026-10-23 at 2 09 58 PM" src="https://github.com/user-attachments/assets/f6603e87-7693-4dd2-a16d-87ef7a534cac" />

## 🚀 Features

- Workout activity tracking and storage.
- AI-powered workout analysis and recommendations.
- Microservices architecture for scalability.
- Asynchronous communication using RabbitMQ.
- Secure authentication and role-based authorization using Keycloak.
- Service discovery using Netflix Eureka.

## 🛠️ Tech Stack

- **Backend:** Java, Spring Boot
- **Databases:** MySQL, MongoDB
- **Messaging:** RabbitMQ
- **Security:** Keycloak, JWT
- **AI Integration:** Google Gemini API
- **Testing:** Postman
- **Containerization:** Docker

## 🏗️ Architecture

The platform consists of User Service, Activity Service, AI Service, API Gateway, Eureka Server, and Config Server.

RabbitMQ enables asynchronous communication between the Activity Service and AI Service, allowing workout activities to be processed without waiting for AI analysis to finish.

## 🔮 Future Enhancements

- Develop a user-friendly frontend.
- Add workout progress dashboards with visual performance tracking.
- Provide personalized workout recommendations on a weekly, monthly, and yearly basis.

## 👨‍💻 Author

**Amey Nemmaniwar**  
B.Tech, NIT Warangal

