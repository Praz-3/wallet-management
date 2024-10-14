FROM postgres:14-alpine

RUN apk upgrade --no-cache \
    && rm -rf /tmp/* /var/cache/apk/*
COPY init.sql /docker-entrypoint-initdb.d/
