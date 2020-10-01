package com.one.mongoreactivehumanresources.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.one.mongoreactivehumanresources.documents.Job;
import com.one.mongoreactivehumanresources.documents.Language;
import com.one.mongoreactivehumanresources.documents.enums.Risk;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class JobDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    private Risk risk;
    private double minSalary;
    private double maxSalary;

    public JobDto(String id, String name, Risk risk, double minSalary, double maxSalary) {
        this.id = id;
        this.name = name;
        this.risk = risk;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

    public JobDto(Job job) {
        this(job.getId(), job.getName(), job.getRisk(), job.getMinSalary(), job.getMaxSalary());
    }
}
