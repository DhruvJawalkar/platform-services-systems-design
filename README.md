# Platform Services Systems Design MVP

This repository is a hands-on MVP for practicing backend platform systems design concepts with Java services on Kubernetes.

Phase 1 focuses on:

- a Spring Boot `orders-service`
- Docker packaging
- Kubernetes deployment primitives
- health probes and baseline configuration

Phase 2 adds:

- a platform-owned Java service template contract
- a second Spring Boot service, `payments-service`
- a multi-service build path for paved-road adoption

The next implementation phase adds:

- HTTP integration from `orders-service` to `payments-service`
- correlation ID propagation across service boundaries
- Prometheus metrics registry support via actuator

The current observability phase adds:

- an OpenTelemetry Collector as the shared telemetry entrypoint
- Prometheus scraping through the collector
- a Grafana deployment with a pre-provisioned dashboard and datasource

## Repository Layout

- `services/orders-service` - initial product service
- `services/payments-service` - second product service created from the template
- `platform/java-service-template` - platform-owned golden-path contract
- `infra/kubernetes` - Kubernetes manifests for local deployment
- `infra/observability` - Prometheus, Grafana, and OpenTelemetry Collector manifests
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
$env:MAVEN_OPTS='-Dmaven.repo.local=C:/Users/dhruv/OneDrive/Desktop/Labs/platform-services-systems-design/.m2/repository'
mvn clean package
```

### Build the container image

```powershell
docker build -t orders-service:phase1 services/orders-service
docker build -t payments-service:phase2 services/payments-service
```

If you are using `kind`, load the image into the cluster:

```powershell
kind load docker-image payments-service:phase2 --name platform-demo
kind load docker-image orders-service:phase1 --name platform-demo
```

### Deploy to Kubernetes

```powershell
kubectl apply -f infra/kubernetes/namespace.yaml
kubectl apply -f infra/kubernetes/orders-service-configmap.yaml
kubectl apply -f infra/kubernetes/orders-service.yaml
kubectl apply -f infra/kubernetes/payments-service-configmap.yaml
kubectl apply -f infra/kubernetes/payments-service.yaml
kubectl apply -f infra/observability/otel-collector-config.yaml
kubectl apply -f infra/observability/otel-collector.yaml
kubectl apply -f infra/observability/prometheus-config.yaml
kubectl apply -f infra/observability/prometheus.yaml
kubectl apply -f infra/observability/grafana-datasources.yaml
kubectl apply -f infra/observability/grafana-dashboards.yaml
kubectl apply -f infra/observability/grafana.yaml
```

### Port-forward the service

```powershell
kubectl -n platform-demo port-forward svc/orders-service 8080:80
```

Then open:

- `http://localhost:8080/api/v1/orders/health`
- `http://localhost:8080/actuator/health`
- `http://localhost:8080/actuator/prometheus`

And port-forward the payments service in another terminal if needed:

```powershell
kubectl -n platform-demo port-forward svc/payments-service 8081:80
```

- `http://localhost:8081/api/v1/payments/health`
- `http://localhost:8081/actuator/prometheus`

Port-forward the observability stack when needed:

```powershell
kubectl -n platform-demo port-forward svc/prometheus 9090:9090
kubectl -n platform-demo port-forward svc/grafana 3000:3000
```

Then open:

- `http://localhost:9090`
- `http://localhost:3000` with `admin` / `admin`

## Next Phases

- add tracing and logs backends such as Tempo and Loki behind the collector
- add progressive delivery with Argo Rollouts
- deepen cross-service request flow between orders and payments
