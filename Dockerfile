FROM openjdk:17
EXPOSE 8090
ADD target/parcel-cost-api.jar parcel-cost-api.jar
ENTRYPOINT [ "java", "-jar", "parcel-cost-api.jar" ]