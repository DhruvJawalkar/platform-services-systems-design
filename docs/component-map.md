# Component Map

## Platform-owned components

- `platform/java-service-template`
- Kubernetes manifest conventions under `infra/kubernetes`
- observability infrastructure under `infra/observability`
- local cluster configuration under `infra/local-cluster`
- shared build and deployment scripts under `scripts`

## Product-owned components

- `services/orders-service`
- `services/payments-service`

## Current integration points

- both services share the same Spring Boot baseline
- both services expose actuator health probes
- both services expose actuator Prometheus metrics
- both services use the same `platform.service.*` config contract
- both services can be built from the root Maven aggregator
- `orders-service` calls `payments-service` over HTTP
- correlation IDs propagate through the request chain
- the OpenTelemetry Collector scrapes service metrics and exposes them to Prometheus
- Grafana visualizes service metrics through a provisioned Prometheus datasource
- Tempo stores traces exported by the collector
- Promtail ships Kubernetes pod logs into Loki
- Grafana is provisioned with Prometheus, Loki, and Tempo datasources

## Next integration point

The next phase will instrument the services to emit real trace spans into the collector and Tempo.
