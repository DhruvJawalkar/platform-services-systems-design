# Phase 1 Architecture

Phase 1 establishes the minimal backbone for the platform MVP.

## Components

- `orders-service` implemented with Spring Boot
- Docker image for local packaging
- Kubernetes `Deployment`, `Service`, and `ConfigMap`
- Spring Boot Actuator health endpoints for runtime probing

## Purpose

This phase proves the most basic golden path:

1. build a Java service
2. package it into a container
3. deploy it to Kubernetes
4. operate it with health-aware deployment primitives

Future phases will layer in platform concerns like observability, rollout safety, and reusable templates.
