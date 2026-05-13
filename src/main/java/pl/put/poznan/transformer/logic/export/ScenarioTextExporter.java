package pl.put.poznan.transformer.logic.export;

import pl.put.poznan.transformer.logic.model.Scenario;
import pl.put.poznan.transformer.logic.model.Step;

import java.util.List;

/**
 * Exports a scenario into a plain-text document with hierarchical numbering.
 * The output is intended for requirement documentation and preserves the
 * nesting structure of sub-scenarios.
 */
public class ScenarioTextExporter {

    /**
     * Converts the supplied scenario into formatted text.
     *
     * Example:
     *
     * Title: Book addition
     * Actors: Librarian
     * System actors: System
     *
     * 1. Librarian selects options to add a new book item
     * 2. A form is displayed.
     * 3. Librarian provides the details of the book.
     * 4. IF: Librarian wishes to add copies of the book
     * 4.1. Librarian chooses to define instances
     * 4.2. System presents defined instances
     * 4.3. FOR EACH: instance
     * 4.3.1. Librarian chooses to add an instance
     * 4.3.2. System prompts to enter the instance details
     * 4.3.3. Librarian enters the instance details and confirms them.
     * 4.3.4. System informs about the correct addition of an instance and presents the updated list of instances.
     5. Librarian confirms book addition.
     * 6. System informs about the correct addition of the book.
     *
     *
     * @param scenario scenario to export
     * @return formatted text containing metadata and numbered steps
     */
    public String export(Scenario scenario) {
        StringBuilder builder = new StringBuilder();

        // title
        builder.append("Title: ")
                .append(scenario.getTitle())
                .append("\n");

        // actors
        builder.append("Actors: ");

        if (scenario.getActors().isEmpty()) {
            builder.append("None");
        } else {
            builder.append(String.join(", ", scenario.getActors()));
        }

        builder.append("\n");

        // system actors
        builder.append("System actors: ");

        if (scenario.getSystemActors().isEmpty()) {
            builder.append("None");
        } else {
            builder.append(String.join(", ", scenario.getSystemActors()));
        }

        builder.append("\n\n");

        // steps
        appendSteps(builder, scenario.getSteps(), "");

        return builder.toString();
    }

    /**
     * Recursively appends numbered steps to the target buffer.
     *
     * @param builder text builder collecting the export result
     * @param steps list of steps to append at the current nesting level
     * @param prefix current numbering prefix inherited from the parent step
     */
    private void appendSteps(StringBuilder builder,
                             List<Step> steps,
                             String prefix) {

        for (int i = 0; i < steps.size(); i++) {
            Step step = steps.get(i);

            String number = prefix.isEmpty()
                    ? String.valueOf(i + 1)
                    : prefix + "." + (i + 1);

            builder.append(number)
                    .append(". ")
                    .append(step.getText())
                    .append("\n");

            appendSteps(builder, step.getSubSteps(), number);
        }
    }
}
