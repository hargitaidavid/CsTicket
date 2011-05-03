#!/bin/sh

export JRE_HOME="/System/Library/Frameworks/JavaVM.framework/Versions/1.6.0/Home"
cd install/apache-tomcat-7.0.11/bin/
chmod a+x *.sh
./startup.sh
cd ../../..