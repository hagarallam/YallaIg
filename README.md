# YallaIg - IGCSE Learning Platform

An all-in-one platform for IGCSE students to manage courses, access resources, and engage in community discussions.

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-green)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://www.mysql.com/)
[![Swagger](https://img.shields.io/badge/Swagger-3.0-success)](https://swagger.io/)
[![Railway](https://img.shields.io/badge/Deployed%20on-Railway-0B0D0E)](https://railway.app)

## Key Features

### Student Portal
- Secure authentication (JWT + Refresh Tokens)
  - Registration/Login
  - Password reset
  - "Remember me" option
- E-wallet system for course enrollment
- PayPal integration for balance recharge

### Admin Dashboard
- User/Content Management
  - Courses, instructors & resources
  - Teacher applications
- Automated Excel reports (email delivery)

### Community Forum
- Post discussions & comments
- Gamified points system (upvotes/downvotes)

## Tech Stack

**Backend:**
- Spring Boot 3 + JPA/Hibernate
- Spring Security + JWT
- Redis (Caching)
- MapStruct (DTO Mapping)
- JpaAuditing
- Scheduling

**APIs & Docs:**
- RESTful API design
- Swagger UI Documentation
- PayPal Integration

**Infrastructure:**
- MySQL Database
- Railway (Staging Deployment)

## API Documentation
Explore interactive API docs:  
[![Swagger UI](https://img.shields.io/badge/Swagger-UI-%23Clojure)](https://yallaig-production.up.railway.app/swagger-ui/index.html)


