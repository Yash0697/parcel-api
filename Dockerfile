FROM openjdk:17
EXPOSE 8090
ADD target/parcel-cost-api.jar parcel-cost-api.jar
ENTRYPOINT [ "java", "-jar", "-DCOUPON_API=false", "parcel-cost-api.jar" ]