package pl.put.poznan.transformer.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.put.poznan.transformer.logic.model.Scenario;
import pl.put.poznan.transformer.rest.dto.ScenarioDto;

import javax.validation.Valid;

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
     * @param request the scenario sent by the client
     * @return the same scenario after parsing
     */
    @PostMapping(path = "/parse", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Scenario parse(@Valid @RequestBody ScenarioDto request) {
        logger.info("Got parse request for scenario \"{}\"", request.getTitle());
        Scenario scenario = request.toDomain();
        logger.debug("Scenario \"{}\" has {} top-level steps",
                scenario.getTitle(), scenario.getSteps().size());
        return scenario;
    }
}
