FROM java

COPY ./target/api-gateway.jar /

EXPOSE 8000


CMD ["java","-jar","api-gateway.jar"]