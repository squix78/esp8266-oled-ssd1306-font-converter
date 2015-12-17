#!/usr/bin/env bash
PATH=/var/lib/openshift/566d81410c1e6676960001c5/app-root/data/node-v5.2.0-linux-x64/bin/:$PATH
export HOME=$HOME/app-root/runtime/repo
npm -v
npm install bower
source "\$OPENSHIFT_REPO_DIR/.openshift/lib/setup_custom_nodejs_env"
npm install --only=dev
bower install
grunt