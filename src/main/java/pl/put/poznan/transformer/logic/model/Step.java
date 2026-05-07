package pl.put.poznan.transformer.logic.model;

import pl.put.poznan.transformer.logic.visitor.ScenarioVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * One step of a scenario. A step has a text and can have sub-steps. The text
 * may start with a keyword like IF, ELSE or FOR EACH.
 */
public class Step {

    /** Keywords that a step text can start with. */
    public static final List<String> KEYWORDS = Collections.unmodifiableList(
            java.util.Arrays.asList("IF", "ELSE", "FOR EACH"));

    /** Text of the step. */
    private final String text;
    /** Sub-steps of this step. */
    private final List<Step> subSteps;

    /**
     * Creates a new step.
     *
     * @param text     text of the step
     * @param subSteps sub-steps (can be empty)
     */
    public Step(String text, List<Step> subSteps) {
        this.text = text == null ? "" : text;
        this.subSteps = subSteps == null ? new ArrayList<>() : new ArrayList<>(subSteps);
    }

    /** @return the step's text content */
    public String getText() {
        return text;
    }

    /** @return an unmodifiable view of the direct sub-steps of this step */
    public List<Step> getSubSteps() {
        return Collections.unmodifiableList(subSteps);
    }

    /**
     * Lets a visitor visit this step and all its sub-steps.
     *
     * @param visitor the visitor to use
     */
    public void accept(ScenarioVisitor visitor) {
        visitor.visit(this);
        for (Step child : subSteps) {
            child.accept(visitor);
        }
    }
}
