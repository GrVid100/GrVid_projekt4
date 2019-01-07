FROM openjdk:8-jdk-alpine AS build
WORKDIR /projekt-scs
COPY . ./
RUN ./gradlew --no-daemon --stacktrace clean bootJar

FROM openjdk:8-jre-alpine
RUN apk add --no-cache bash
WORKDIR /projekt-scs

COPY wait-for-it.sh .
COPY --from=build /projekt-scs/build/libs/*.jar projekt-scs.jar

CMD java -jar projekt-scs.jar
