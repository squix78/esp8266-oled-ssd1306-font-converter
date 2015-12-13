#!/usr/bin/env bash
export HOME=$HOME/app-root/runtime/repo
npm -v
npm cache clean
npm install bower
npm install --only=dev
bower install
grunt