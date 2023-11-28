#!/bin/sh

if [ "$#" -ne 2 ]
then
  echo "Incorrect Usage: Not all argument were provided."
  echo "Example Usage:"
  echo "./fill_placeholders.sh i5300000 my-cluster"
  exit 1
fi

cd "$(dirname "$0")" || exit 1

sed -i.bak "s/<YOUR C\/D\/I-NUMBER>/$1/g" deployment/apps/2_users.yaml || exit 1
sed -i.bak "s/<YOUR C\/D\/I-NUMBER>/$1/g" deployment/apps/3_greetings.yaml || exit 1
sed -i.bak "s/<CLUSTER>/$2/g" deployment/apps/3_greetings.yaml || exit 1

echo "All placeholders were replaced successfully."