package pl.put.poznan.transformer.logic.visitor;

import pl.put.poznan.transformer.logic.model.Scenario;
import pl.put.poznan.transformer.logic.model.Step;

/**
 * Visitor that counts steps starting with conditional keywords.
 * Keywords are defined in {@link Step#KEYWORDS}.
 */
public class KeywordCountVisitor implements ScenarioVisitor {

    /** Running total of steps whose text starts with a supported keyword. */
    private int count = 0;

    /**
     * Retrieves the calculated total.
     *
     * @return count of steps starting with keywords
     */
    public int getCount() {
        return count;
    }

    @Override
    public void visit(Scenario scenario) {
        // The scenario node is not counted. Only its steps matter here.
    }

    @Override
    public void visit(Step step) {
        String text = step.getText();
        if (text == null) {
            return;
        }

        for (String keyword : Step.KEYWORDS) {
            if (text.startsWith(keyword)) {
                count++;
                break;
            }
        }
    }
}
