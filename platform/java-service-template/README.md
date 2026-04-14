# Java Service Template

This directory documents the paved-road contract for Java services in this repository.

## Template Goals

- start new services with production-friendly defaults
- standardize Spring Boot dependencies and layout
- provide a consistent API, health, config, Docker, and Kubernetes story
- reduce service bootstrap time for product teams

## Required Template Primitives

Every new service should include:

- Spring Boot web and actuator starters
- request validation
- `application.yml` with `platform.service.*` config binding
- liveness and readiness probes via actuator
- Docker packaging
- Kubernetes `Deployment`, `Service`, and `ConfigMap`
- basic controller, service, domain, and test structure

## Standard Directory Shape

```text
service-name/
  pom.xml
  Dockerfile
  .dockerignore
  src/main/java/com/platform/<service>/
    api/
    config/
    domain/
    service/
  src/main/resources/application.yml
  src/test/java/com/platform/<service>/
```

## Service Creation Checklist

1. Copy the baseline structure from an existing service.
2. Replace package and artifact names.
3. Preserve shared configuration keys under `platform.service`.
4. Preserve actuator exposure and probe configuration.
5. Add service-specific API and domain objects.
6. Add Kubernetes manifests using the same label and resource conventions.

## Current Services Using This Template

- `orders-service`
- `payments-service`
