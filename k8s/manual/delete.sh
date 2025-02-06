#!/bin/bash
clear

tput setaf 5
echo -e "Deleting the deployments"

tput setaf 2
kubectl delete -f ./manifests/

kubectl delete ing contract-ingress
