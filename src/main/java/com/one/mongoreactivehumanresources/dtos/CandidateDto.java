package com.one.mongoreactivehumanresources.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.one.mongoreactivehumanresources.documents.Candidate;
import com.one.mongoreactivehumanresources.documents.Training;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CandidateDto {

    private String id;

    private String name;

    private String dni;

    private double salary;

    private List<Training> trainings;

    public CandidateDto(List<Training> trainings) {
        this.trainings = trainings;
    }

    public CandidateDto(Candidate candidate) {
        this.id = candidate.getId();
        this.name = candidate.getName();
        this.dni = candidate.getDni();
        this.salary = candidate.getSalary();
        this.trainings = candidate.getTrainings();
    }
}
