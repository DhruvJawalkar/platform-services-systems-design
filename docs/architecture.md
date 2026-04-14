# Phase 1 Architecture

Phases 1 and 2 establish the minimal backbone for the platform MVP.

## Components

- `orders-service` implemented with Spring Boot
- `payments-service` implemented with Spring Boot using the same template contract
- Docker image for local packaging
- Kubernetes `Deployment`, `Service`, and `ConfigMap`
- Spring Boot Actuator health endpoints for runtime probing
- a platform-owned Java service template contract
- HTTP service-to-service integration from orders to payments
- correlation ID propagation for request tracking
- Prometheus-compatible metrics endpoints
- OpenTelemetry Collector, Prometheus, and Grafana for local observability
- Tempo for trace storage and Loki for log storage
- OpenTelemetry SDK-based distributed tracing inside both services

## Purpose

This phase proves the most basic golden path:

1. create services from the same paved-road structure
2. build Java services from a shared contract
3. package them into containers
4. deploy them to Kubernetes
5. operate them with health-aware deployment primitives
6. trace a request manually across service boundaries with shared correlation IDs
7. inspect metrics through Prometheus and Grafana via a shared collector
8. inspect pod logs in Loki and prepare a path for distributed traces in Tempo
9. emit and inspect real end-to-end traces for the orders-to-payments request path

Future phases will layer in platform concerns like observability, rollout safety, and reusable templates.
