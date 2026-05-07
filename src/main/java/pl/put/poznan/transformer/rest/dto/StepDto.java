package pl.put.poznan.transformer.rest.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Step data sent in the request body. Has the step text and a list of
 * sub-steps.
 */
public class StepDto {

    @NotNull(message = "step.text must not be null")
    private String text;

    @Valid
    private List<StepDto> subSteps = new ArrayList<>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<StepDto> getSubSteps() {
        return subSteps;
    }

    public void setSubSteps(List<StepDto> subSteps) {
        this.subSteps = subSteps == null ? new ArrayList<>() : subSteps;
    }
}
