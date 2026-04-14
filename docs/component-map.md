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
- both services use the same `platform.service.*` config contract
- both services can be built from the root Maven aggregator

## Next integration point

The next phase will connect services through a real request path and add shared observability.
