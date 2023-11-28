#!/bin/sh

if [ "$#" -ne 1 ]
then
  echo "Incorrect Usage: The C/D/I-number was not provided."
  exit 1
fi

cd "$(dirname "$0")" || exit 1

./greetings/push_image.sh "$1" || exit 1
./users/push_image.sh "$1" || exit 1