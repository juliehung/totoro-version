FROM adoptopenjdk/openjdk8:debian-jre
LABEL maintainer "Jim-Lin <acgsong.tw@yahoo.com.tw>"

# Add a jhipster user to run our application so that it doesn't need to run as root
RUN useradd -ms /bin/bash jhipster
WORKDIR /home/jhipster

ADD entrypoint.sh entrypoint.sh
RUN chmod 755 entrypoint.sh && chown jhipster:jhipster entrypoint.sh
USER jhipster

ENTRYPOINT ["./entrypoint.sh"]

ADD app.war app.war
