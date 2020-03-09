FROM adoptopenjdk/openjdk8:debian-jre

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JHIPSTER_SLEEP=0 \
    JAVA_OPTS=""

ARG DEBCONF_NOWARNINGS=yes

RUN apt-get update && apt-get install -y --no-install-recommends wget

# Add a jhipster user to run our application so that it doesn't need to run as root
RUN useradd -ms /bin/bash jhipster
WORKDIR /home/jhipster

ADD entrypoint.sh entrypoint.sh
RUN chmod 755 entrypoint.sh && chown jhipster:jhipster entrypoint.sh

USER jhipster

# Comment embedded postgres to reduce docker image building duration
#RUN mkdir .embedpostgresql && wget -P .embedpostgresql http://get.enterprisedb.com/postgresql/postgresql-10.6-1-linux-x64-binaries.tar.gz
#RUN mkdir -p pgembed/pgsql-10.6-1 && tar -C pgembed/pgsql-10.6-1 -zxvf .embedpostgresql/postgresql-10.6-1-linux-x64-binaries.tar.gz

ENTRYPOINT ["./entrypoint.sh"]

# totoro admin server port
EXPOSE 8080
# moquette mqtt server port
EXPOSE 1883
# moquette mqtt websocket port
EXPOSE 8081

ADD *.war app.war
