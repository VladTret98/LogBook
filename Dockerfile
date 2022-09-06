FROM tomcat:8-jdk11-openjdk

COPY /target/LogBook-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]

