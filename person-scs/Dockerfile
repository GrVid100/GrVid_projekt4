FROM openjdk:8-jdk-alpine AS build
WORKDIR /person-scs
COPY . ./
RUN ./gradlew --no-daemon --stacktrace clean bootJar

FROM openjdk:8-jre-alpine
RUN apk add --no-cache bash
WORKDIR /person-scs

COPY wait-for-it.sh .
COPY --from=build /person-scs/build/libs/*.jar person-scs.jar

CMD java -jar person-scs.jar
