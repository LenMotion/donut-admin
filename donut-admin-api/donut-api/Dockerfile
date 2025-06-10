FROM amazoncorretto:17.0.10-al2023
WORKDIR /app
COPY ./target/donut-api-2.4.0.jar app.jar
# COPY --from=builder ./donut-api/target/donut-api-1.0.0.jar .
# 设定时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE 10001
CMD ["java", "-Dspring.profiles.active=prod", "-jar", "/app/app.jar"]
RUN echo "🎉 架 🎉 设 🎉 成 🎉 功 🎉"