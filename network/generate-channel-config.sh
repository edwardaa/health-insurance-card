#!/bin/bash
#
# Exit on first error, print all commands.
set -e

#Start from here
echo -e "\nGenerating channel config (if any)"

if [ ! -d "config" ]; then
    mkdir config
fi


configtxgen -profile TwoOrgsOrdererGenesis -outputBlock ./config/genesis.block
configtxgen -profile TwoOrgsChannel -outputCreateChannelTx ./config/channel.tx -channelID cardchannel
configtxgen -profile TwoOrgsChannel -outputAnchorPeersUpdate ./config/InsurerMSPanchors.tx -channelID cardchannel -asOrg InsurerOrgMSP
configtxgen -profile TwoOrgsChannel -outputAnchorPeersUpdate ./config/HospitalMSPanchors.tx -channelID cardchannel -asOrg HospitalOrgMSP

