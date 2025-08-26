# Copilot Instructions for AI Coding Agents

## Project Overview
This is a Spring Boot Java application located in `src/main/java/com/hka/intranet/messageprotocol/matrix/springboot/demo/`. The main entry point is `DemoApplication.java`. The project follows standard Spring Boot conventions for structure and configuration.

## Architecture & Components
- **Main Application:** `DemoApplication.java` is the bootstrap class. All business logic should be placed in new service, controller, or model classes under the same package hierarchy.
- **Resources:** Configuration and static assets are in `src/main/resources/`. Use `application.properties` for environment-specific settings.
- **Testing:** Tests are located in `src/test/java/com/hka/intranet/messageprotocol/matrix/springboot/demo/`. Use JUnit for unit and integration tests.

## Developer Workflows
- **Build:** Use Gradle for builds. Run `./gradlew build` (Linux/macOS) or `gradlew.bat build` (Windows) from the project root.
- **Run:** Start the application with `./gradlew bootRun` or `gradlew.bat bootRun`.
- **Test:** Execute tests using `./gradlew test` or `gradlew.bat test`.
- **Debug:** Standard Spring Boot debugging applies. Use IDE breakpoints or run with `--debug` flag.

## Conventions & Patterns
- **Package Structure:** Follow the existing package hierarchy for new classes. Group related functionality by feature.
- **Configuration:** Place all configuration in `application.properties`. Avoid hardcoding values in Java files.
- **Static/Template Files:** Put static assets in `resources/static/` and templates in `resources/templates/`.
- **Gradle:** All dependency management and build logic is handled via `build.gradle` and `settings.gradle`.

## Integration Points
- **External Dependencies:** Managed via Gradle. Add new dependencies to `build.gradle`.
- **Service Boundaries:** If adding REST endpoints, create controllers in the main package and annotate with `@RestController`.
- **Data Flow:** Use Spring's dependency injection for service communication. Avoid manual instantiation of beans.

## Examples
- To add a new REST endpoint, create a class in `src/main/java/com/hka/intranet/messageprotocol/matrix/springboot/demo/`:
  ```java
  @RestController
  public class ExampleController {
      @GetMapping("/hello")
      public String hello() {
          return "Hello, World!";
      }
  }
  ```
- To add a property, update `src/main/resources/application.properties`:
  ```properties
  example.property=value
  ```

## Key Files & Directories
- `DemoApplication.java`: Main entry point
- `application.properties`: Configuration
- `build.gradle`: Build and dependencies
- `src/main/resources/static/`: Static files
- `src/main/resources/templates/`: Templates
- `src/test/java/...`: Tests

---
For unclear or incomplete sections, please provide feedback to improve these instructions.
