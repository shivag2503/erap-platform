ERAP — Enterprise Retail Analytics Platform
> A full-stack, event-driven, microservices-based retail platform built as a comprehensive learning portfolio project.
![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.1-brightgreen?style=flat-square&logo=springboot)
![React](https://img.shields.io/badge/React-18-blue?style=flat-square&logo=react)
![Python](https://img.shields.io/badge/Python-3.11-yellow?style=flat-square&logo=python)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-3.7-black?style=flat-square&logo=apachekafka)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=flat-square&logo=postgresql)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)
---
What is ERAP?
ERAP is an end-to-end retail platform that covers the complete software engineering lifecycle — Java backend, React frontend, AI/ML integration, DevOps, testing, and documentation. It is aligned to a 54-week learning plan and grows phase by phase from a basic product catalog to a full LLM-powered analytics platform.
What it does:
Sells products to customers via a responsive storefront
Manages orders, inventory, payments, and customer loyalty end to end
Provides real-time and predictive analytics using AI/ML
Streams all business events through an event-driven Kafka backbone
Powers an AI chatbot with RAG for product and policy queries
---
Architecture
```
React Frontend (:5173)
        │
        ▼
API Gateway (:8080)  ←── JWT Auth, Rate Limiting, Routing
        │
        ├──▶ Product Service    (:8081)  ──┐
        ├──▶ Order Service      (:8082)    │
        ├──▶ Customer Service   (:8083)    ├──▶ PostgreSQL (:5432)
        ├──▶ Payment Service    (:8084)    │    (separate schemas)
        ├──▶ Analytics Service  (:8085)  ──┘
        └──▶ Notification Svc   (:8086)
                    │
                    ▼
              Apache Kafka (:9092)
                    │
         ┌──────────┴──────────┐
         ▼                     ▼
   Redis (:6379)        FastAPI ML (:8000)
   Cache/Sessions       Fraud · Forecast · Sentiment · RAG
```
---
Tech Stack
Backend
Technology	Version	Purpose
Java	17 LTS	Primary backend language
Spring Boot	3.3.1	Core framework for all microservices
Spring Cloud Gateway	4.1.x	API Gateway — routing, auth, rate limiting
Spring Security + JWT	6.x / 0.12.5	Authentication and authorisation
Spring Data JPA	included	ORM layer — PostgreSQL mapping
Spring Kafka	included	Kafka producer and consumer
Spring AI	1.0.x	LLM integration, RAG pipelines (Phase 5)
Flyway	10.12.0	Database schema migrations
MapStruct	1.5.5	DTO to entity mapping
Lombok	1.18.32	Boilerplate reduction
Springdoc OpenAPI	2.5.0	Swagger UI auto-generation
Frontend
Technology	Version	Purpose
React	18	UI framework
Vite	5.x	Build tool and dev server
React Router	6.x	Client-side routing
Axios	1.7.x	HTTP client
Recharts	2.12.x	Charts and dashboards
Tailwind CSS	3.4.x	Utility-first styling
TanStack Query	5.x	Server state management
Zustand	4.x	Global state management
AI / ML
Technology	Version	Purpose
Python	3.11	ML language
FastAPI	0.111.x	ML inference REST service
scikit-learn	1.5.x	Classical ML — XGBoost, K-Means
PyTorch	2.3.x	Deep learning — LSTM, CNN
HuggingFace Transformers	4.41.x	DistilBERT sentiment analysis
ONNX Runtime	1.18.x	Java-native model inference
Spring AI	1.0.x	RAG pipeline, embeddings
MLflow	2.13.x	ML experiment tracking (Phase 6)
Data & Messaging
Technology	Version	Purpose
PostgreSQL	16	Primary database — all services
PgVector	0.7.x	Vector storage for RAG embeddings
Redis	7.2	Cache, sessions, JWT blacklist
Apache Kafka	3.7 (KRaft)	Event streaming backbone
DevOps & Testing
Technology	Version	Purpose
Docker	4.x	Kafka + Kafka UI containers
GitHub Actions	—	CI/CD pipelines
JUnit 5 + Mockito	5.10.x	Unit testing
Testcontainers	1.19.x	Integration testing
Spotless	2.43.0	Code formatting enforcement
Swagger / OpenAPI	3.0	API documentation
---
Microservices
Service	Port	DB Schema	Kafka Role	Status
API Gateway	:8080	—	None	🔄 In Progress
Product Service	:8081	erap_product	Producer	🔄 In Progress
Order Service	:8082	erap_order	Producer + Consumer	⏳ Planned
Customer Service	:8083	erap_customer	Consumer	⏳ Planned
Payment Service	:8084	erap_payment	Producer + Consumer	⏳ Planned
Analytics Service	:8085	erap_analytics	Consumer	🔄 In Progress
Notification Service	:8086	erap_notification	Consumer	⏳ Planned
ML Service (FastAPI)	:8000	—	None	⏳ Phase 3
React Frontend	:5173	—	—	🔄 In Progress
Kafka UI	:8090	—	—	⏳ Phase 2
> Legend: ✅ Done · 🔄 In Progress · ⏳ Planned
---
Kafka Topics
Topic	Producer	Consumers
`order.placed`	Order Service	Payment, Analytics, Notification
`order.completed`	Order Service	Customer, Analytics, Notification, ML
`order.cancelled`	Order Service	Analytics, Notification
`payment.success`	Payment Service	Order, Customer, Analytics, Notification
`payment.failed`	Payment Service	Order, Analytics, Notification
`fraud.detected`	Payment Service	Analytics, Notification
`product.stock.low`	Product Service	Analytics, Notification, ML
`ml.segmentation.result`	Analytics/ML	Customer
`ml.recommendation.ready`	Analytics/ML	Notification
---
AI / ML Modules
Module	Algorithm	Learning Phase	Status
Demand Forecasting	XGBoost + lag features	Phase 3	⏳ Planned
Fraud Detection	XGBoost + SMOTE + SHAP	Phase 3	⏳ Planned
Customer Segmentation	K-Means + PCA	Phase 3	⏳ Planned
Sales Prediction	LSTM (PyTorch) + ONNX	Phase 4	⏳ Planned
Product Image Recognition	ResNet18 + ONNX	Phase 4	⏳ Planned
Sentiment Analysis	DistilBERT (HuggingFace)	Phase 4	⏳ Planned
Smart Recommendations	LLM Embeddings + PgVector	Phase 5	⏳ Planned
AI Chatbot (RAG)	Spring AI + PgVector + LLM	Phase 5	⏳ Planned
AI Agent	Spring AI Functions + ReAct	Phase 5	⏳ Planned
---
Prerequisites
Make sure the following are installed before running ERAP locally:
Java 17 (Temurin/Eclipse distribution) — `java -version`
Maven 3.9+ — `mvn -version`
Node.js 20 LTS — `node -v`
Python 3.11 — `python3 --version`
Docker Desktop — for Kafka + Kafka UI
PostgreSQL 16 — installed directly on your OS
Redis 7.2 — via WSL2 (Windows) or native (Mac/Linux)
Git 2.45+ — `git --version`
---
Getting Started
1. Clone the repository
```bash
git clone https://github.com/shivag2503/erap-platform.git
cd erap-platform
git checkout develop
```
2. Set up PostgreSQL
Create the required schemas in PostgreSQL:
```sql
CREATE SCHEMA erap_product;
CREATE SCHEMA erap_order;
CREATE SCHEMA erap_customer;
CREATE SCHEMA erap_payment;
CREATE SCHEMA erap_analytics;
CREATE SCHEMA erap_notification;
```
3. Start Redis
```bash
# WSL2 (Windows)
wsl redis-server

# Mac/Linux
redis-server
```
4. Start Kafka (Phase 2+)
```bash
docker-compose up -d
# Kafka UI available at http://localhost:8090
```
5. Configure environment
Each service has an `application.yml`. Copy and update with your local values:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres?currentSchema=erap_analytics
    username: your_db_username
    password: your_db_password
```
6. Run services
Start services in this order:
```bash
# 1. API Gateway
cd erap-api-gateway && mvn spring-boot:run

# 2. Analytics Service (first visible service)
cd erap-analytics-service && mvn spring-boot:run

# 3. Product Service
cd erap-product-service && mvn spring-boot:run

# 4. Frontend
cd erap-frontend && npm install && npm run dev
```
7. Verify
URL	What you see
http://localhost:8080/swagger-ui.html	All API docs aggregated
http://localhost:8085/swagger-ui.html	Analytics Service Swagger
http://localhost:8081/swagger-ui.html	Product Service Swagger
http://localhost:5173	React frontend
http://localhost:8090	Kafka UI (Phase 2+)
http://localhost:8000/docs	ML Service Swagger (Phase 3+)
---
Project Structure
```
erap-platform/
  ├── .github/
  │   └── workflows/           ← CI/CD pipelines per service
  ├── erap-api-gateway/        ← Spring Cloud Gateway :8080
  ├── erap-product-service/    ← Product catalog :8081
  ├── erap-order-service/      ← Orders and cart :8082
  ├── erap-customer-service/   ← Auth and loyalty :8083
  ├── erap-payment-service/    ← Payments :8084
  ├── erap-analytics-service/  ← Analytics dashboard :8085
  ├── erap-notification-service/ ← Email/SMS/WebSocket :8086
  ├── erap-ml-service/         ← Python FastAPI ML :8000
  ├── erap-frontend/           ← React + Vite :5173
  ├── erap-data/               ← ETL scripts, datasets, notebooks
  ├── erap-docs/               ← Architecture diagrams, ERDs, API specs
  ├── docker-compose.yml       ← Kafka + Kafka UI
  └── pom.xml                  ← Parent Maven POM
```
---
Development Workflow
```bash
# Always start from develop
git checkout develop && git pull origin develop

# Create feature branch
git checkout -b feature/<service>-<description>

# Code, then format before committing
mvn spotless:apply

# Commit with Conventional Commits
git commit -m "feat(<service>): description"

# Push and open PR → develop
git push origin feature/<service>-<description>
```
Branch strategy: `main` (production) ← `develop` (integration) ← `feature/*` / `bugfix/*`
Commit format: `type(scope): description` — e.g. `feat(product-svc): add inventory management`
---
Build Phases
Phase	Learning Weeks	What gets built
Phase 1	1–8	API Gateway + Product Service + Analytics Service + React dashboard
Phase 2	9–16	Order + Customer + Payment + Notification + Kafka events + JWT auth
Phase 3	17–26	Fraud detection + Demand forecasting + Customer segmentation (ML)
Phase 4	27–36	LSTM sales prediction + Image recognition + Sentiment analysis (DL)
Phase 5	37–46	RAG chatbot + Smart recommendations + AI agent (LLMs)
Phase 6	47–54	MLflow + DVC + Docker + CI/CD + Prometheus + Grafana (MLOps)
---
API Documentation
All service APIs are documented via Swagger / OpenAPI 3.0.
Aggregated docs: `http://localhost:8080/swagger-ui.html`
Individual service docs: `http://localhost:{port}/swagger-ui.html`
ML Service docs: `http://localhost:8000/docs`
JWT authentication is supported in Swagger UI — click Authorize and paste your token.
---
Testing
```bash
# Unit tests
mvn test -pl erap-product-service

# Integration tests (requires Docker)
mvn verify -pl erap-product-service

# All services
mvn test

# Code formatting check
mvn spotless:check

# Auto-format
mvn spotless:apply

# Python ML tests
cd erap-ml-service && pytest tests/ -v

# React tests
cd erap-frontend && npm test
```
---
Documentation
All project documentation lives in `erap-docs/`:
Document	Location
Architecture diagram	`erap-docs/architecture/system-overview.png`
Database ERDs	`erap-docs/db/`
Kafka topic registry	`erap-docs/kafka-topics.md`
API Postman collections	`erap-docs/postman/`
ML model cards	`erap-ml-service/models/MODEL_CARD.md`
Setup guide	`erap-docs/setup-guide.md`
Runbook	`erap-docs/runbook.md`
---
Contributing
This is a solo learning project. Development follows a strict Git workflow:
Branch from `develop` — never commit directly to `main` or `develop`
Follow Conventional Commits for all commit messages
Open a PR for every change — CI must pass before merging
Squash and merge only — no regular merge commits
Run `mvn spotless:apply` before every commit
---
License
This project is licensed under the MIT License.
---
Built as a 54-week learning portfolio project — Java · Spring Boot · React · Python · Kafka · AI/ML