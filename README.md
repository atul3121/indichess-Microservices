🏆 IndiChess – Microservices Backend

A microservices-based backend for a Chess platform built using Spring Boot, Spring Cloud, Eureka, API Gateway, JWT Authentication, and MySQL.

🧩 Architecture

Eureka Server → Service Discovery

API Gateway → Single entry point

User Service → Authentication & User Management

MySQL → Database

JWT → Authentication

OAuth2 (Google Login) → Optional social login

⚙️ Services & Ports
Service	Port
Eureka Server	8761
API Gateway	8083
User Service	8081
📡 API Gateway Base URL
http://localhost:8083/api

👤 User Service Endpoints (via API Gateway)
🔐 Authentication
✅ Signup
POST /api/signup


Body:

{
  "username": "atul",
  "emailId": "atul@gmail.com",
  "password": "123456",
  "country": "India"
}

✅ Login
POST /api/login


Body:

{
  "username": "atul",
  "password": "123456"
}


Response:

JWT token returned

JWT stored in HTTP-only cookie

🏠 Home (Protected)
GET /api/home


Header:

Cookie: JWT=your_token

👥 User APIs
✅ Hello Test
GET /api/hello

✅ Get User (test)
GET /api/user/username

🔐 OAuth2 (Google Login)
GET /api/oauth2/authorization/google


After login:

JWT is generated

User auto-saved in database

Redirects to frontend

🗄️ Database

Database Name:

indichess


Table:

users


Main fields:

user_id

user_name

email_id

password

country

rating

pfp_url

🔁 Service Registration (Eureka)

Dashboard:

http://localhost:8761


You should see:

API-GATEWAY

USER-SERVICE

▶️ Run Order

Start Eureka Server

Start User Service

Start API Gateway

🧪 Testing (Postman)

Signup:

POST http://localhost:8083/api/signup


Login:

POST http://localhost:8083/api/login


Protected:

GET http://localhost:8083/api/home

🛠 Tech Stack

Java 21

Spring Boot 3.3.5

Spring Security

Spring Cloud Gateway

Netflix Eureka

JWT

OAuth2

MySQL

Lombok
