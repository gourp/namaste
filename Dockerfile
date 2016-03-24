FROM jboss/base-jdk:8

ADD target/namaste.jar /

EXPOSE 8080

CMD java -jar /namaste.jar
