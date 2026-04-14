# Component Map

## Platform-owned components

- `platform/java-service-template`
- Kubernetes manifest conventions under `infra/kubernetes`
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

## Next integration point

The next phase will add shared telemetry backends and dashboards on top of the current service-to-service flow.
