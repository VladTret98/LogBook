FROM postgres:11
FROM tomcat:9-jdk11

COPY target/BeerShop.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]

