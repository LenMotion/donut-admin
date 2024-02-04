# FROM maven:3.9.6-amazoncorretto-17 AS builder
# MAINTAINER LenMotion 'lenmotion@sina.com'
# WORKDIR /app
# COPY . ./
# RUN mvn clean install -DskipTests
# RUN echo "🎉 编 🎉 译 🎉 成 🎉 功 🎉"
# FROM amazoncorretto:17.0.10-al2023
# WORKDIR /app
# COPY --from=builder ./donut-api/target/donut-api-1.0.0.jar .
# EXPOSE 10001
# CMD ["java", "--spring.profiles.active=prod", "-jar", "donut-api-1.0.0.jar"]
# RUN echo "🎉 架 🎉 设 🎉 成 🎉 功 🎉"

FROM amazoncorretto:17.0.10-al2023
WORKDIR /app
COPY ./donut-api/target/donut-api-1.0.0.jar app.jar
# COPY --from=builder ./donut-api/target/donut-api-1.0.0.jar .
# 设定时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE 10001
CMD ["java", "-Dspring.profiles.active=prod", "-jar", "/app/app.jar"]
RUN echo "🎉 架 🎉 设 🎉 成 🎉 功 🎉"