minikube start

minikube docker-env | Invoke-Expression # PowerShell

docker build -t ing/cloud-storage .

kubectl apply -f kube\all-pods.yaml

kubectl get pods
