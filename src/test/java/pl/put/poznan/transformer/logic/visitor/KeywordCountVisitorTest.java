package pl.put.poznan.transformer.logic.visitor;

import org.junit.jupiter.api.Test;
import pl.put.poznan.transformer.logic.model.Scenario;
import pl.put.poznan.transformer.logic.model.Step;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KeywordCountVisitorTest {

    @Test
    void testNoKeywords() {
        KeywordCountVisitor visitor = new KeywordCountVisitor();
        Step step1 = new Step("Just a simple step", Collections.emptyList());
        Step step2 = new Step("Another simple step", Collections.emptyList());

        visitor.visit(step1);
        visitor.visit(step2);

        assertEquals(0, visitor.getCount());
    }

    @Test
    void testStartsWithKeyword() {
        KeywordCountVisitor visitor = new KeywordCountVisitor();
        Step step1 = new Step("IF something happens", Collections.emptyList());
        Step step2 = new Step("ELSE do this", Collections.emptyList());
        Step step3 = new Step("FOR EACH element", Collections.emptyList());

        visitor.visit(step1);
        visitor.visit(step2);
        visitor.visit(step3);

        assertEquals(3, visitor.getCount());
    }

    @Test
    void testKeywordInMiddle() {
        KeywordCountVisitor visitor = new KeywordCountVisitor();
        Step step = new Step("This step has an IF in the middle", Collections.emptyList());

        visitor.visit(step);

        assertEquals(0, visitor.getCount());
    }

    @Test
    void testNullText() {
        KeywordCountVisitor visitor = new KeywordCountVisitor();
        Step step = new Step(null, Collections.emptyList());

        visitor.visit(step);

        assertEquals(0, visitor.getCount());
    }

    @Test
    void testEmptyScenario() {
        KeywordCountVisitor visitor = new KeywordCountVisitor();
        Scenario scenario = new Scenario("Empty", Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        visitor.visit(scenario);

        assertEquals(0, visitor.getCount());
    }

}
