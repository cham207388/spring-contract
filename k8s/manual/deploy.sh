#!/bin/bash
clear

tput setaf 5
echo -e "\n*******************************************************************************************************************"
echo -e "Spring contract deployment and service"
echo -e "*******************************************************************************************************************"

tput setaf 2
kubectl apply -f ./manifests/

tput setaf 5
echo -e "\n \n*******************************************************************************************************************"
echo -e "Finding the IP address of the host to create the nip.io Ingress rule"
echo -e "*******************************************************************************************************************"
tput setaf 2
# for macbook `uname -m`=arm64
export hostip=$(ifconfig | grep "inet " | grep -v 127.0.0.1 | awk '{print $2}' | head -n 1)

# for linux `uname -m`=x86_64 = aarch64
# export hostip=$(hostname  -I | cut -f1 -d' ')
echo -e "\nFound the Host IP: $hostip"

tput setaf 3
echo -e "\n*******************************************************************************************************************"
echo -e "Creating the Ingress rule using $hostip"
echo -e "*******************************************************************************************************************\n"
tput setaf 2
sleep 2
envsubst < ingress-resource.yaml | kubectl create -f - --namespace default

tput setaf 7
echo -e "\n \n*******************************************************************************************************************"
echo -e "Ingress URL host: $(kubectl get ingress -o jsonpath="{.items[*].spec.rules[*].host}")"
echo -e "\n You can now browse to the NGINX pod using the nip.io domain from any machine on your local network"
echo -e "******************************************************************************************************************* \n\n"
tput setaf 2
