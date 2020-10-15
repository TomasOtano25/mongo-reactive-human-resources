package com.one.mongoreactivehumanresources.documents;

import com.one.mongoreactivehumanresources.dtos.CompetencyDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode
@ToString
@Data
@Document
public class Competency {
    @Id
    private String id;
    private String description;
    private boolean state;

    public Competency() {
        this.state = true;
    }

    public Competency(String description) {
        this();
        this.description = description;
    }

    public Competency(CompetencyDto competencyDto) {
        this.id = competencyDto.getId();
        this.description = competencyDto.getDescription();
        this.state = true;
    }

}
