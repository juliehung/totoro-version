FROM debian:stretch

RUN apt-get update && apt-get install -y --no-install-recommends nginx

ADD default /etc/nginx/sites-available/default
ADD public/ /var/www/html/

RUN service nginx reload

# forward request and error logs to docker log collector
RUN ln -sf /dev/stdout /var/log/nginx/access.log \
    && ln -sf /dev/stderr /var/log/nginx/error.log

EXPOSE 80

STOPSIGNAL SIGTERM

CMD ["nginx", "-g", "daemon off;"]
