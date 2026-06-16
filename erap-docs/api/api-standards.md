# ERAP — API Standards

## Base URL
All endpoints follow: /api/v1/{service}/**

## Versioning
/api/v1/ prefix on all routes
Breaking changes → bump to /api/v2/

## HTTP Methods
GET    → retrieve data (never changes state)
POST   → create new resource
PUT    → full update of resource
PATCH  → partial update of resource
DELETE → soft delete (sets is_active = false)

## HTTP Status Codes
200 → success (GET, PUT, PATCH)
201 → created (POST)
204 → no content (DELETE)
400 → validation failed
401 → not authenticated
403 → not authorised
404 → resource not found
409 → duplicate resource
422 → business rule violation
429 → rate limit exceeded
500 → unexpected error
502 → external gateway error
503 → service unavailable

## Pagination
All list endpoints support:
?page=0&size=20&sort=createdAt,desc
Max size: 100
Response: PagedResponse<T>

## Error Response Shape
{
"timestamp": "2026-06-16T10:30:00",
"status": 404,
"error": "Not Found",
"message": "Resource not found",
"path": "/api/v1/analytics/sales/daily",
"service": "erap-analytics-service"
}

## Authentication
All endpoints except /auth/** require:
Authorization: Bearer <JWT token>

## Service-specific API contracts
See GitHub Issues for each service:
Analytics Service → Issue #5
Product Service   → Issue #8
Order Service     → Issue #10
...