package com.one.mongoreactivehumanresources.documents;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WorkExperience {
    private String company;
    private String job;
    private LocalDate from;
    private LocalDate to;
    private double salary;
}
