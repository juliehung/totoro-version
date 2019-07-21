#!/bin/sh

echo "The application will start in ${JHIPSTER_SLEEP}s..." && sleep ${JHIPSTER_SLEEP}
exec java ${JAVA_OPTS} -Dpgembed.cached_path=/home/jhipster/pgembed -Djava.security.egd=file:/dev/./urandom -jar "${HOME}/app.war" "$@"
