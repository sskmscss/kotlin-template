FROM openjdk:11-slim
EXPOSE 8080

ENTRYPOINT ["./entrypoint.sh"]

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

ARG APP_NAME=template-request-handler

ENV AH_HOME /opt/service
ENV APP_HOME $AH_HOME/$APP_NAME

ENV USERNAME service
ENV GROUPNAME service

WORKDIR $APP_HOME

COPY entrypoint.sh .
COPY target/*.jar lib/app.jar

RUN groupadd -r $GROUPNAME && useradd --no-log-init -r -g $GROUPNAME $USERNAME \
    && chown -R $USERNAME:$GROUPNAME $APP_HOME \
    && chmod 755 *.sh

USER $USERNAME