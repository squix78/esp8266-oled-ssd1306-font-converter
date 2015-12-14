#!/usr/bin/env bash
PATH=/var/lib/openshift/566d81410c1e6676960001c5/app-root/data/node-v4.2.3-linux-x64/bin/:$PATH
export HOME=$HOME/app-root/runtime/repo
npm -v
npm cache clean
npm install bower
npm install --only=dev
bower install
grunt