openshift-diy-nodejs08
========================

Thanks for the great work by [razorinc](https://github.com/razorinc/redis-openshift-example) and [creationix](https://github.com/creationix/nvm/), this repo let you test node.js v0.8 (v0.9 seems OK too) with OpenShift DIY application type. It will first check for pre-compiled linux version, then compile from source if not found.

[node-supervisor](https://github.com/isaacs/node-supervisor) is used to automatically restart the node.js app if somehow crashed.

Usage
-----

Create an DIY app

    rhc app create -t diy-0.1 -a yourapp

Add this repository

    cd yourapp
    git remote add nodejs08 -m master git://github.com/eddie168/openshift-diy-nodejs08.git
    git pull -s recursive -X theirs nodejs08 master

Then push the repo to openshift

    git push

If pre-compiled binary is not available, first push will take a while to finish.

You can specify the node.js script to start with in `package.json` as described [here](https://openshift.redhat.com/community/kb/kb-e1048-how-can-i-run-my-own-nodejs-script).

Check the end of the message for node version:

    remote: Starting application...
    remote: Node Version:
    remote: { http_parser: '1.0',
    remote:   node: '0.8.16',
    remote:   v8: '3.11.10.25',
    remote:   ares: '1.7.5-DEV',
    remote:   uv: '0.8',
    remote:   zlib: '1.2.3',
    remote:   openssl: '1.0.0f' }
    remote: nohup supervisor server.js >/var/lib/stickshift/xxxxxxxxxxxxxxxxxx/diy-0.1/logs/server.log 2>&1 &
    remote: Done

In this case it is node `v0.8.16`.

Now open your openshift app in browser and you should see the standard openshift sample page. Enjoy!!

Settings
--------

Edit `config_diy.json`

    "nodejs": {
      "version": "v0.8.16",
      "removeOld": false
    }

- `version`: change node.js version
- `removeOld`: delete previous installed node.js binarys

`commit` and then `push` to reflect the changes to the OpenShift app.

**Note that `v0.6.x` won't work with this method.**

