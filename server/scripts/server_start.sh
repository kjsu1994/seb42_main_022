#!/usr/bin/env bash
cd /home/ubuntu/build
sudo nohup java -jar community-0.0.1-SNAPSHOT.jar --jasypt.encryptor.password=$mypassword > /dev/null 2> /dev/null < /dev/null &