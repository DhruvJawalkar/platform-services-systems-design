kubectl apply -f infra/observability/otel-collector-config.yaml
kubectl apply -f infra/observability/otel-collector.yaml
kubectl apply -f infra/observability/prometheus-config.yaml
kubectl apply -f infra/observability/prometheus.yaml
kubectl apply -f infra/observability/grafana-datasources.yaml
kubectl apply -f infra/observability/grafana-dashboards.yaml
kubectl apply -f infra/observability/grafana.yaml
