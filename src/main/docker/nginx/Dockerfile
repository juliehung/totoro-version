FROM debian:stretch

RUN apt-get update && apt-get install -y --no-install-recommends nginx

ADD default /etc/nginx/sites-available/default
ADD public/ /var/www/html/

RUN service nginx reload

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
