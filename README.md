# Shields
A restful HTTP service based on [Spark](http://sparkjava.com/) that will redirect based on it's requests to appropriate shield and homepage URLs for:

   - codecov
   - github
   - gradle plugins
   - jcenter
   - maven central

The actual shields are generated by the repo itself, or [img.shields.io](https://img.shields.io/badge).

For usage documentation goto the [OpenShift](https://www.openshift.com/) deployment at [http://shields-nwillc.rhcloud.com](http://shields-nwillc.rhcloud.com).

## Build and Run
Shields is built using gradle, and provides a run script:

    $ ./run.sh

## Deployed 
The shields app is deployed at: [http://shields-nwillc.rhcloud.com](http://shields-nwillc.rhcloud.com)

-----
[![ISC License](http://shields-nwillc.rhcloud.com/shield/tldrlegal?package=ISC)](http://shields-nwillc.rhcloud.com/homepage/tldrlegal?package=ISC)
[![Build Status](http://shields-nwillc.rhcloud.com/shield/travis-ci?path=nwillc&package=shields)](http://shields-nwillc.rhcloud.com/homepage/travis-ci?path=nwillc&package=shields)
[![Coverage Status](http://shields-nwillc.rhcloud.com/shield/codecov?path=github/nwillc&package=shields)](http://shields-nwillc.rhcloud.com/homepage/codecov?path=github/nwillc&package=shields)

