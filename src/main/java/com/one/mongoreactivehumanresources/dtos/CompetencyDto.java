package com.one.mongoreactivehumanresources.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.one.mongoreactivehumanresources.documents.Competency;
import com.one.mongoreactivehumanresources.documents.Language;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CompetencyDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    public CompetencyDto(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public CompetencyDto(Competency competency) {
        this(competency.getId(), competency.getDescription());
    }
}
