package pl.put.poznan.transformer.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import pl.put.poznan.transformer.logic.export.ScenarioTextExporter;
import pl.put.poznan.transformer.logic.model.Scenario;
import pl.put.poznan.transformer.logic.visitor.StepCountVisitor;
import pl.put.poznan.transformer.logic.visitor.KeywordCountVisitor;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

/**
 * REST controller for the Scenario Quality Checker. Receives a scenario as
 * JSON and returns the result of an analysis as JSON.
 */
@RestController
@RequestMapping(path = "/api/scenarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScenarioController {

    /** Logger used for incoming requests. */
    private static final Logger logger = LoggerFactory.getLogger(ScenarioController.class);

    /**
     * Reads a scenario from JSON and sends it back. Used to check that the
     * input has the right format.
     *
     * @param scenario the scenario sent by the client
     * @return the same scenario after parsing
     */
    @PostMapping(path = "/parse", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Scenario parse(@Valid @RequestBody Scenario scenario) {
        logger.info("Got parse request for scenario \"{}\"", scenario.getTitle());
        logger.debug("Scenario \"{}\" has {} top-level steps",
                scenario.getTitle(), scenario.getSteps().size());
        return scenario;
    }

    /**
     * Counts all steps in the scenario, including sub-steps at any depth.
     * An empty scenario returns zero.
     *
     * @param scenario the scenario sent by the client
     * @return JSON with the total step count, e.g. {@code {"stepCount": 5}}
     */
    @PostMapping(path = "/step-count", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Integer> stepCount(@Valid @RequestBody Scenario scenario) {
        logger.info("Got step-count request for scenario \"{}\"", scenario.getTitle());
        StepCountVisitor visitor = new StepCountVisitor();
        scenario.accept(visitor);
        logger.debug("Scenario \"{}\" has {} step(s) in total",
                scenario.getTitle(), visitor.getCount());
        return Collections.singletonMap("stepCount", visitor.getCount());
    }

    /**
     * Counts how many steps in the scenario begin with a conditional keyword 
     * (e.g., IF, ELSE, FOR EACH).
     *
     * @param scenario the scenario sent by the client
     * @return JSON with the keyword count, e.g. {@code {"keywordCount": X}}
     */
    @PostMapping(path = "/keyword-count", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Integer> keywordCount(@Valid @RequestBody Scenario scenario) {
        logger.info("Got keyword-count request for scenario \"{}\"", scenario.getTitle());
        KeywordCountVisitor visitor = new KeywordCountVisitor();
        scenario.accept(visitor);
        logger.debug("Scenario \"{}\" has {} step(s) with keywords",
                scenario.getTitle(), visitor.getCount());
        return Collections.singletonMap("keywordCount", visitor.getCount());
    }

    /**
     * Exports scenario as a downloadable text file
     * with numbered steps.
     *
     * @param scenario scenario to export
     * @return downloadable text file
     */
    @PostMapping(
            path = "/export",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> export(@Valid @RequestBody Scenario scenario) {

        logger.info("Got export request for scenario \"{}\"",
                scenario.getTitle());

        ScenarioTextExporter exporter = new ScenarioTextExporter();

        String text = exporter.export(scenario);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"scenario.txt\""
                )
                .header(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.TEXT_PLAIN_VALUE
                )
                .body(text);
    }
}
