package com.one.mongoreactivehumanresources.documents;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;



@ToString
@Data
@NoArgsConstructor
@Document
public class Candidate {
    private String id;
    private String name;

    @Indexed(unique = true)
    private String dni;

    private double salary;

    private List<Training> trainings;

    private List<WorkExperience> workExperiences;

    private List<Contact> contacts;

    @DBRef
    private User user;

    public Candidate(String name, String dni, double salary, List<Training> trainings,
                     List<WorkExperience> workExperiences, List<Contact> contacts, User user) {
        this.name = name;
        this.dni = dni;
        this.salary = salary;
        this.trainings = trainings;
        this.workExperiences = workExperiences;
        this.contacts = contacts;
        this.user = user;
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
