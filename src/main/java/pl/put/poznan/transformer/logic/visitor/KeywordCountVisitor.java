package pl.put.poznan.transformer.logic.visitor;

import pl.put.poznan.transformer.logic.model.Scenario;
import pl.put.poznan.transformer.logic.model.Step;

/**
 * Visitor that counts steps starting with conditional keywords.
 * Keywords are defined in {@link Step#KEYWORDS}.
 */
public class KeywordCountVisitor implements ScenarioVisitor {

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
        // Ignored, we only count steps
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
