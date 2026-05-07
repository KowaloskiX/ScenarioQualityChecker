package pl.put.poznan.transformer.rest.dto;

import pl.put.poznan.transformer.logic.model.Scenario;
import pl.put.poznan.transformer.logic.model.Step;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Scenario data sent in the request body. Spring fills this object from JSON
 * and {@link #toDomain()} turns it into a {@link Scenario}.
 */
public class ScenarioDto {

    @NotBlank(message = "title must not be blank")
    private String title;

    @NotNull(message = "actors must not be null")
    private List<String> actors = new ArrayList<>();

    @NotNull(message = "systemActors must not be null")
    private List<String> systemActors = new ArrayList<>();

    @NotNull(message = "steps must not be null")
    @Valid
    private List<StepDto> steps = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors == null ? new ArrayList<>() : actors;
    }

    public List<String> getSystemActors() {
        return systemActors;
    }

    public void setSystemActors(List<String> systemActors) {
        this.systemActors = systemActors == null ? new ArrayList<>() : systemActors;
    }

    public List<StepDto> getSteps() {
        return steps;
    }

    public void setSteps(List<StepDto> steps) {
        this.steps = steps == null ? new ArrayList<>() : steps;
    }

    /**
     * Builds a {@link Scenario} object from the DTO data.
     *
     * @return a new scenario object
     */
    public Scenario toDomain() {
        return new Scenario(title, actors, systemActors, mapSteps(steps));
    }

    private static List<Step> mapSteps(List<StepDto> dtos) {
        if (dtos == null) {
            return new ArrayList<>();
        }
        return dtos.stream()
                .map(dto -> new Step(dto.getText(), mapSteps(dto.getSubSteps())))
                .collect(Collectors.toList());
    }
}
