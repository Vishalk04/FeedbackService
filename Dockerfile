FROM java:alpine
ADD target/feedback-service.jar feedback-service.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","feedback-service.jar"]
