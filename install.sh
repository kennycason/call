#!/bin/sh

mvn clean package
echo $'#!/bin/sh\n java -jar ~/workspace/run/target/run-1.0.jar "$@"' > /usr/local/bin/run
chmod +x /usr/local/bin/run