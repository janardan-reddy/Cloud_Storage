#!/usr/bin/env bash

minikube start

eval $(minikube docker-env)             # unix shells

docker build -t ing/cloud-storage .

kubectl apply -f kube/all-pods.yaml

kubectl get pods
