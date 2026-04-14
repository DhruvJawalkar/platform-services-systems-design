# Template Contract

This contract defines what belongs to the platform template versus each product service.

## Platform-owned defaults

- build and runtime dependency choices
- actuator and probe exposure
- environment-driven config naming
- Docker packaging pattern
- Kubernetes manifest structure
- baseline resource requests and limits
- service label conventions

## Product-team customization points

- domain models
- business endpoints
- downstream dependencies
- service-specific validation rules
- future persistence logic

## Why this matters

This is the first step toward adoption by paved road rather than hard enforcement. Teams should be able to copy a working shape that already carries the right defaults.
