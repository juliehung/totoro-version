FROM adoptopenjdk/openjdk8:debian-jre
LABEL maintainer "Jim-Lin <acgsong.tw@yahoo.com.tw>"

# Add a jhipster user to run our application so that it doesn't need to run as root
RUN useradd -ms /bin/bash jhipster
WORKDIR /home/jhipster

ADD entrypoint.sh entrypoint.sh
RUN chmod 755 entrypoint.sh && chown jhipster:jhipster entrypoint.sh

USER jhipster

COPY postgresql-10.6-1-linux-x64-binaries.tar.gz .embedpostgresql/postgresql-10.6-1-linux-x64-binaries.tar.gz
RUN mkdir -p pgembed/pgsql-10.6-1
RUN tar -C pgembed/pgsql-10.6-1 -zxvf .embedpostgresql/postgresql-10.6-1-linux-x64-binaries.tar.gz

ENTRYPOINT ["./entrypoint.sh"]

ADD app.war app.war
