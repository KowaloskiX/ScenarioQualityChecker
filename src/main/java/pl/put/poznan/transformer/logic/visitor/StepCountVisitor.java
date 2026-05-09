package pl.put.poznan.transformer.logic.visitor;

import pl.put.poznan.transformer.logic.model.Scenario;
import pl.put.poznan.transformer.logic.model.Step;

/**
 * Visitor that counts all steps in a scenario, including sub-steps at any
 * depth. The scenario itself is not counted.
 */
public class StepCountVisitor implements ScenarioVisitor {

    /** Running total of steps seen so far. */
    private int count = 0;

    @Override
    public void visit(Scenario scenario) {
        // root, not counted
    }

    @Override
    public void visit(Step step) {
        count++;
    }

    /**
     * Returns the number of steps counted after the visitor has walked the
     * scenario.
     *
     * @return total number of steps and sub-steps
     */
    public int getCount() {
        return count;
    }
}
