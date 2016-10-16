FROM java

COPY ./target/api-gateway.jar /

CMD ["java","-jar","api-gateway.jar"]