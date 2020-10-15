package com.one.mongoreactivehumanresources.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.one.mongoreactivehumanresources.documents.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private List<WorkExperience> workExperiences;

    private List<Contact> contacts;

    private List<LanguageDto> languages;

    private List<CompetencyDto> competencies;

    private JobDto job;

    public CandidateDto(List<Training> trainings, List<WorkExperience> workExperiences,
                        List<Contact> contacts) {
        this.trainings = trainings;
        this.workExperiences = workExperiences;
        this.contacts = contacts;
    }

    public CandidateDto(Candidate candidate) {
        this.id = candidate.getId();
        this.name = candidate.getName();
        this.dni = candidate.getDni();
        this.salary = candidate.getSalary();
        this.trainings = candidate.getTrainings();
        this.workExperiences = candidate.getWorkExperiences();
        this.contacts = candidate.getContacts();
        this.languages = candidate.getLanguages().stream().map(language -> new LanguageDto(language)).collect(Collectors.toList());
        this.competencies = candidate.getCompetencies().stream().map(competency -> new CompetencyDto(competency)).collect(Collectors.toList());
        this.job = new JobDto(candidate.getJob());
    }
}
