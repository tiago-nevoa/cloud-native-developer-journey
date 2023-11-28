#!/bin/sh

if [ "$#" -ne 1 ]
then
  echo "Incorrect Usage: Domain was not provided."
  echo "Example Usage:"
  echo "./create-kubeconfig.sh c-1234567 >> pipeline-kubeconfig.yaml"
  exit 1
fi

DOMAIN=$1

CA=$(kubectl get secret/pipeline-service-account -o jsonpath='{.data.ca\.crt}')
TOKEN=$(kubectl get secret/pipeline-service-account -o jsonpath='{.data.token}' | base64 -d)

echo "apiVersion: v1
kind: Config
current-context: pipeline-service-account
users:
  - name: pipeline-service-account
    user:
      token: >-
        ${TOKEN}
clusters:
  - name: shoot--kyma--${DOMAIN}
    cluster:
      certificate-authority-data: >-
        ${CA}
      server: https://api.${DOMAIN}.kyma.ondemand.com
contexts:
  - context:
      cluster: shoot--kyma--${DOMAIN}
      user: pipeline-service-account
      namespace: default
    name: pipeline-service-account
"
