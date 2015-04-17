#!/bin/bash

if [[ "$DEBUG" == "true" ]]; then
    set -x
fi

set -e
set -o pipefail

sudo yum install -y gnupg

if ! gpg --list-keys 44CE9D82; then
    gpg --import /var/go/tclemson.gpg.key
fi

cp -f /var/go/gradle.properties .

./gradlew uploadArchives

REPO_ID=$(./gradlew nexusStagingList | grep "Repository ID" | cut -d ' ' -f 3 | cut -d ',' -f  1 | sort -r | head -n 1)

./gradlew nexusStagingClose -PrepoId=$REPO_ID
sleep 1m
./gradlew nexusStagingPromote -PrepoId=$REPO_ID