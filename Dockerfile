## Multi-stage build to always compile the Spring Boot app during docker build

FROM eclipse-temurin:21-jdk AS build
ARG SKIP_TESTS=false
ENV SKIP_TESTS=${SKIP_TESTS}
WORKDIR /workspace

# Copy Gradle wrapper and build scripts first (leverage layer caching for deps)
COPY build.gradle settings.gradle gradlew gradlew.bat ./
COPY gradle ./gradle

# Download dependencies (will be cached unless build scripts changed)
RUN ./gradlew --no-daemon dependencies > /dev/null 2>&1 || true

# Copy source code
COPY src ./src

# Run tests (unless SKIP_TESTS=true) then build jar
#RUN if [ "$SKIP_TESTS" = "true" ]; then \
#            ./gradlew --no-daemon clean bootJar -x test; \
#        else \
#            ./gradlew --no-daemon clean test bootJar || { \
#                echo "Some tests didn't complete successfully: Stopping execution"; \
#                exit 1; \
#            }; \
#        fi
        
#skip tests
RUN ./gradlew --no-daemon clean bootJar -x test
# dont skip tests
#RUN ./gradlew --no-daemon clean test bootJar
        
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
COPY --from=build /workspace/build/libs/*SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
