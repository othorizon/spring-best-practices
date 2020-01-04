FROM java:8

MAINTAINER rizon
LABEL site="https://github.com/othorizon/spring-best-practices"

VOLUME /data
WORKDIR /data

COPY conf ./conf
COPY lib ./lib

# replace placeholder by package_jar.sh
EXPOSE ${server_port}

CMD java -Dspring.config.additional-location=file:./conf/application.properties,file:./conf/application.yml -jar ./lib/${jar_name} 2>&1