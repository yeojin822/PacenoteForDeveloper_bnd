ARG ER=032559872243.dkr.ecr.ap-northeast-2.amazonaws.com

FROM $ER/openjdk:11.0.13-jdk as builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM $ER/openjdk:11.0.13-jdk
COPY --from=builder build/libs/*.jar app.jar

ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# ARG ENVIROMENT
ENV APP_NAME=PortfolioPageBuilder_bnd
ENV PORT=8080
ENV PROFILE=dev

ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "/app.jar"]


#docker run -it --rm --name rf-rec -e PROFILE=dev --publish 8080:8080 rf-rec
