package com.one.mongoreactivehumanresources.documents;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WorkExperience {
    private String company;
    private String job;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime from;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime to;
    private double salary;
}
