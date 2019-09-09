FROM openjdk:12-oraclelinux7 as builder

RUN yum install -y -q unzip

ENV GRADLE_VERSION=5.6.2
ENV GRADLE_HOME=/opt/gradle-$GRADLE_VERSION/
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false"
ENV PATH=$PATH:$GRADLE_HOME/bin

RUN mkdir -p /opt/ && cd /opt/ \
    && echo "Downloading gradle.." \
    && curl -sSfL https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip -o gradle-bin.zip \
    && unzip gradle-bin.zip -d /opt/ \
    && rm gradle-bin.zip

RUN mkdir -p /home/app

WORKDIR /home/app

COPY function /home/app

RUN gradle build

FROM openfaas/of-watchdog:0.5.4 as watchdog

FROM openjdk:12-oraclelinux7 as ship

COPY --from=watchdog /fwatchdog /usr/bin/fwatchdog
RUN chmod +x /usr/bin/fwatchdog

RUN groupadd --system app \
    && adduser app --system -g app

WORKDIR /home/app

COPY --from=builder /home/app/build/libs/function.jar ./function.jar

RUN chown -R app /home/app

USER app

ENV upstream_url="http://127.0.0.1:8082"
ENV mode="http"
ENV fprocess="java -jar function.jar"

EXPOSE 8080

HEALTHCHECK --interval=5s CMD [ -e /tmp/.lock ] || exit 1

CMD ["fwatchdog"]
