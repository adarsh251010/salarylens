# SalaryLens 💰

> AI-powered salary negotiation platform that helps professionals negotiate better compensation using real-time market analysis and personalized negotiation scripts.

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0-green)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue)](https://www.postgresql.org)
[![Redis](https://img.shields.io/badge/Redis-Cloud-red)](https://redis.io)

## Problem Statement

Salary negotiation is high-stakes yet most professionals negotiate blind. SalaryLens combines market data with AI to generate personalized negotiation strategies in seconds.

## Architecture

```
Client → JWT Auth → Spring Boot API → PostgreSQL
                                    → Redis Cache (90x faster repeated requests)
                                    → OpenAI GPT (Async job processing)
```

## Key Engineering Decisions

| Decision | Choice | Reason |
|----------|--------|--------|
| AI Processing | Async + Job Polling | Avoids 28s blocking — instant UX |
| Caching | Redis Cloud | 90x faster, reduced OpenAI API costs |
| Auth | JWT + BCrypt | Stateless, scalable, secure |
| Job Security | Owner-bound JobIDs | Prevents cross-user data access |
| ORM | Spring Data JPA | Clean repository pattern |

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 4.0 |
| Database | PostgreSQL 17 |
| Cache | Redis Cloud |
| Auth | JWT + Spring Security |
| AI | OpenAI GPT-3.5 via RestTemplate |
| Async | CompletableFuture |

## Features

- JWT Authentication with BCrypt password hashing
- AI Salary Analysis — market analysis + negotiation script
- Async Job Processing — non-blocking AI calls with job polling
- Redis Caching — 90x faster repeated responses
- Job Ownership Security — users access only their own results
- Full CRUD with validation and pagination
- Global Exception Handling

## API Reference

### Auth
```
POST /auth/register
POST /auth/login
```

### Salary Data
```
POST   /api/salaries
GET    /api/salaries
GET    /api/salaries/{id}
PUT    /api/salaries/{id}
DELETE /api/salaries/{id}
GET    /api/salaries/location?location=Bangalore
GET    /api/salaries/experience?years=2
```

### AI Analysis
```
POST /api/ai/analyze           → returns jobId instantly
GET  /api/ai/result/{jobId}    → poll for result
```

## Performance

| Scenario | Response Time |
|----------|--------------|
| Async job submission | ~500ms |
| Cached AI request | ~314ms |
| First AI request | ~28s (OpenAI) |
| Standard API calls | <100ms |

## Local Setup

```bash
# Clone
git clone https://github.com/adarsh251010/salarylens.git
cd salarylens

# Set environment variables
OPENAI_API_KEY=your-key
REDIS_HOST=your-host
REDIS_PORT=your-port
REDIS_PASSWORD=your-password
DB_PASSWORD=your-db-password
JWT_SECRET=your-jwt-secret

# Run
mvn spring-boot:run
```

## What I Learned

- Async job systems for long-running AI tasks
- Redis caching strategy for cost and latency optimization
- JWT security with ownership-based resource access
- Spring Boot production patterns

---

**Author**: Adarsh Rai  
**Portfolio**: https://netflix-style-portfolio-alpha.vercel.app/  
**LinkedIn**: [Add your LinkedIn]