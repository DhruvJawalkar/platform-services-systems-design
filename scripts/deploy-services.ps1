kubectl apply -f infra/kubernetes/namespace.yaml
kubectl apply -f infra/kubernetes/orders-service-configmap.yaml
kubectl apply -f infra/kubernetes/orders-service.yaml
kubectl apply -f infra/kubernetes/payments-service-configmap.yaml
kubectl apply -f infra/kubernetes/payments-service.yaml
