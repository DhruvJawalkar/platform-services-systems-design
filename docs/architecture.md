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

## Purpose

This phase proves the most basic golden path:

1. create services from the same paved-road structure
2. build Java services from a shared contract
3. package them into containers
4. deploy them to Kubernetes
5. operate them with health-aware deployment primitives
6. trace a request manually across service boundaries with shared correlation IDs

Future phases will layer in platform concerns like observability, rollout safety, and reusable templates.
