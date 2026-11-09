# syntax=docker/dockerfile:1
ARG JAVA_VERSION=21

FROM docker.io/library/eclipse-temurin:${JAVA_VERSION}-jdk-jammy AS build
WORKDIR /workspace

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -B -ntp -DskipTests dependency:go-offline

COPY src/ src/
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -B -ntp -DskipTests package dependency:copy-dependencies \
      -DincludeGroupIds=com.microsoft.playwright \
      -DoutputDirectory=target/playwright \
    && cp target/JobPilot-*.jar /workspace/app.jar

FROM docker.io/library/eclipse-temurin:${JAVA_VERSION}-jre-jammy AS runtime
ENV PLAYWRIGHT_BROWSERS_PATH=/ms-playwright \
    PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD=1
WORKDIR /app

RUN --mount=type=bind,from=build,source=/workspace/target/playwright,target=/tmp/playwright \
    apt-get update \
    && apt-get install -y --no-install-recommends curl \
    && java -cp '/tmp/playwright/*' com.microsoft.playwright.CLI install --with-deps chromium \
    && chmod -R a+rX /ms-playwright \
    && rm -rf /var/lib/apt/lists/* \
    && groupadd --gid 10001 jobpilot \
    && useradd --uid 10001 --gid jobpilot --create-home jobpilot

COPY --from=build --chown=jobpilot:jobpilot /workspace/app.jar ./app.jar
ENV SPRING_PROFILES_ACTIVE=docker
USER jobpilot
EXPOSE 8080
STOPSIGNAL SIGTERM
HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3 \
    CMD curl --fail --silent --show-error http://127.0.0.1:8081/actuator/health/readiness || exit 1
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
