#!/bin/sh
mvn clean
mvn deploy
scp target/euu.war root@umbrella.psi.ch:/tmp
ssh root@umbrella.psi.ch /root/r.sh
