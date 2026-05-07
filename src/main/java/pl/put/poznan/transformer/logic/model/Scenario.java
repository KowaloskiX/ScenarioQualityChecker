package pl.put.poznan.transformer.logic.model;

import pl.put.poznan.transformer.logic.visitor.ScenarioVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A scenario read from JSON. It has a title, a list of actors, a list of
 * system actors and a list of steps. Steps can have sub-steps.
 */
public class Scenario {

    /** Title of the scenario. */
    private final String title;
    /** People or external systems that take part in the scenario. */
    private final List<String> actors;
    /** Systems that respond to the actors. */
    private final List<String> systemActors;
    /** Top-level steps of the scenario. */
    private final List<Step> steps;

    /**
     * Creates a new scenario.
     *
     * @param title        scenario title
     * @param actors       list of actors
     * @param systemActors list of system actors
     * @param steps        list of top-level steps
     */
    public Scenario(String title, List<String> actors, List<String> systemActors, List<Step> steps) {
        this.title = title == null ? "" : title;
        this.actors = actors == null ? new ArrayList<>() : new ArrayList<>(actors);
        this.systemActors = systemActors == null ? new ArrayList<>() : new ArrayList<>(systemActors);
        this.steps = steps == null ? new ArrayList<>() : new ArrayList<>(steps);
    }

    /** @return the scenario title */
    public String getTitle() {
        return title;
    }

    /** @return unmodifiable list of external actors */
    public List<String> getActors() {
        return Collections.unmodifiableList(actors);
    }

    /** @return unmodifiable list of system actors */
    public List<String> getSystemActors() {
        return Collections.unmodifiableList(systemActors);
    }

    /** @return unmodifiable list of top-level steps */
    public List<Step> getSteps() {
        return Collections.unmodifiableList(steps);
    }

    /**
     * Lets a visitor go through the whole scenario. First the visitor sees the
     * scenario, then each step (and its sub-steps).
     *
     * @param visitor the visitor to use
     */
    public void accept(ScenarioVisitor visitor) {
        visitor.visit(this);
        for (Step step : steps) {
            step.accept(visitor);
        }
    }
}
