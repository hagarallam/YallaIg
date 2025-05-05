FROM mysql:8.0.34 AS mysql

COPY docker/mysql/init.sql /docker-entrypoint-initdb.d/

ENV MYSQL_ROOT_PASSWORD=root