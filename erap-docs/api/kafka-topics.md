# ERAP — Kafka Topics Registry

| Topic | Producer | Consumers | Payload |
|---|---|---|---|
| order.placed | Order Service | Payment, Analytics, Notification | order_id, customer_id, total_amount |
| order.completed | Order Service | Customer, Analytics, Notification, ML | order_id, customer_id, total_amount |
| payment.success | Payment Service | Order, Customer, Analytics, Notification | payment_id, order_id, amount |
| payment.failed | Payment Service | Order, Analytics, Notification | payment_id, order_id, reason |
| fraud.detected | Payment Service | Analytics, Notification | payment_id, risk_score |
| product.stock.low | Product Service | Analytics, Notification, ML | product_id, current_stock |