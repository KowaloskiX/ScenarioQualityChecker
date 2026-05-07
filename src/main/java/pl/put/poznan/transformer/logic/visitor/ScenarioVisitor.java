package pl.put.poznan.transformer.logic.visitor;

import pl.put.poznan.transformer.logic.model.Scenario;
import pl.put.poznan.transformer.logic.model.Step;

/**
 * Visitor for a scenario. Classes that want to analyse a scenario implement
 * this interface and are called once for the scenario and once for each step.
 */
public interface ScenarioVisitor {

    /**
     * Called for the scenario.
     *
     * @param scenario the scenario being visited
     */
    void visit(Scenario scenario);

    /**
     * Called for every step (including sub-steps).
     *
     * @param step the step being visited
     */
    void visit(Step step);
}
