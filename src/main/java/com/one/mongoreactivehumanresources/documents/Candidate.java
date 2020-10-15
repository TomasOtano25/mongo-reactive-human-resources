package com.one.mongoreactivehumanresources.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;



@ToString
@Data
@NoArgsConstructor
@Document
public class Candidate {

    @Id
    private String id;
    private String name;

    @Indexed(unique = true)
    private String dni;

    private double salary;

    private List<Training> trainings;

    private List<WorkExperience> workExperiences;

    private List<Contact> contacts;

    @DBRef
    private List<Language> languages;

    @DBRef
    private List<Competency> competencies;

    @DBRef
    private User user;

    @DBRef
    private Job job;

    private Boolean contracted = false;

    public Candidate(String name, String dni, double salary, List<Training> trainings,
                     List<WorkExperience> workExperiences, List<Contact> contacts, User user,
                     List<Language> languages, List<Competency> competencies, Job job) {
        this.name = name;
        this.dni = dni;
        this.salary = salary;
        this.trainings = trainings;
        this.workExperiences = workExperiences;
        this.contacts = contacts;
        this.user = user;
        this.languages = languages;
        this.competencies = competencies;
        this.job = job;
    }

    @Override
    public int hashCode() {
        return this.dni.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj != null && getClass() == obj.getClass() && dni.equals(((Candidate) obj).dni);
    }
}
