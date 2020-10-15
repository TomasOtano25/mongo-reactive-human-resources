package com.one.mongoreactivehumanresources.documents;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@ToString
@Data
@Document
public class Employee {

    @Id
    private String id;
    private String name;

    @Indexed(unique = true)
    private String dni;

    private double salary;

    @DBRef
    private User user;

    @DBRef
    private Job job;

    private LocalDateTime registerDate;

    public Employee() {
        this.registerDate = LocalDateTime.now();
    }

    public Employee(String name, String dni, double salary, User user, Job job) {
        this();
        this.name = name;
        this.dni = dni;
        this.salary = salary;
        this.user = user;
        this.job = job;
    }

    @Override
    public int hashCode() {
        return this.dni.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj != null && getClass() == obj.getClass() && dni.equals(((Employee) obj).dni);
    }
}
