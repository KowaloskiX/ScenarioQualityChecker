package pl.put.poznan.transformer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class of the application. Starts the Spring Boot server.
 */
@SpringBootApplication(scanBasePackages = {"pl.put.poznan.transformer"})
public class ScenarioQualityCheckerApplication {

    /**
     * Starts the application.
     *
     * @param args program arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ScenarioQualityCheckerApplication.class, args);
    }
}
