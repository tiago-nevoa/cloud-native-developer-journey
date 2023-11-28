#!/bin/sh

if [ "$#" -ne 1 ]; then
  echo "Incorrect Usage: The C/D/I-number was not provided."
  exit 1
fi

cd "$(dirname "$0")" || exit 1

mvn package

# The "--platform linux/amd64" is used here to make sure the image works in the remote K8s cluster - not matter if you are using Apple Silicon or not
docker build --platform linux/amd64 -t cc-ms-k8s-training.common.repositories.cloud.sap/users-"$1" . || exit 1
docker login -u "claude" -p "9qR5hbhm7Dzw6BNZcRFv" cc-ms-k8s-training.common.repositories.cloud.sap || exit 1
docker push cc-ms-k8s-training.common.repositories.cloud.sap/users-"$1" || exit 1

if [ "$( kubectl get pod -l app=users -o jsonpath="{.items}")" = "[]" ]; then
  exit 0
fi;
kubectl delete pod $(kubectl get pod -l app=users -o jsonpath="{.items[0].metadata.name}")