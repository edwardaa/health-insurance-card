#!/bin/bash
#
# Exit on first error, print all commands.
set -e

#Start from here
echo -e "\nGenerating crypto-config folder"

if [ ! -d "crypto-config" ]; then
    cryptogen generate --config=./crypto-config.yaml
fi


