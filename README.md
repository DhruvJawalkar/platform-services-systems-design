# Platform Services Systems Design MVP

This repository is a hands-on MVP for practicing backend platform systems design concepts with Java services on Kubernetes.

Phase 1 focuses on:

- a Spring Boot `orders-service`
- Docker packaging
- Kubernetes deployment primitives
- health probes and baseline configuration

## Repository Layout

- `services/orders-service` - initial product service
- `infra/kubernetes` - Kubernetes manifests for local deployment
- `docs` - architecture and implementation notes
- `scripts` - convenience scripts for local setup and deployment

## Phase 1 Quick Start

### Prerequisites

- Java 11+
- Maven 3.8+
- Docker Desktop
- `kubectl`
- `kind` or another local Kubernetes cluster

### Build the service

```powershell
cd services/orders-service
$env:MAVEN_OPTS='-Dmaven.repo.local=C:/Users/dhruv/OneDrive/Desktop/Labs/platform-services-systems-design/.m2/repository'
mvn clean package
```

### Build the container image

```powershell
docker build -t orders-service:phase1 services/orders-service
```

If you are using `kind`, load the image into the cluster:

```powershell
kind load docker-image orders-service:phase1 --name platform-demo
```

### Deploy to Kubernetes

```powershell
kubectl apply -f infra/kubernetes/namespace.yaml
kubectl apply -f infra/kubernetes/orders-service-configmap.yaml
kubectl apply -f infra/kubernetes/orders-service.yaml
```

### Port-forward the service

```powershell
kubectl -n platform-demo port-forward svc/orders-service 8080:80
```

Then open:

- `http://localhost:8080/api/v1/orders/health`
- `http://localhost:8080/actuator/health`

## Next Phases

- add a reusable Java service template
- add observability with OpenTelemetry, Prometheus, Grafana, Loki, and Tempo
- add progressive delivery with Argo Rollouts
- add a second service and cross-service request flow
