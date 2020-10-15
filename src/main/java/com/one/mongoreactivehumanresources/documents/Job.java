package com.one.mongoreactivehumanresources.documents;

import com.one.mongoreactivehumanresources.documents.enums.Risk;
import com.one.mongoreactivehumanresources.dtos.JobDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode
@ToString
@Data
@Document
public class Job {
    @Id
    private String id;
    private String name;
    private Risk risk;
    private double minSalary;
    private double maxSalary;
    private boolean state;

    public Job() {
        this.state = true;
    }

    public Job(String name, Risk risk, double minSalary, double maxSalary) {
        this.name = name;
        this.risk = risk;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.state = true;
    }

    public Job(JobDto jobDto) {
        this.id = jobDto.getId();
        this.name = jobDto.getName();
        this.risk = jobDto.getRisk();
        this.minSalary = jobDto.getMinSalary();
        this.maxSalary = jobDto.getMaxSalary();
        this.state = true;
    }
}
