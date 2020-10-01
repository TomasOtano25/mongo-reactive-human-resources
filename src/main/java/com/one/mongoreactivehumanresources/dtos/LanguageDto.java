package com.one.mongoreactivehumanresources.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.one.mongoreactivehumanresources.documents.Language;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LanguageDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    public LanguageDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public LanguageDto(Language language) {
        this(language.getId(), language.getName());
    }
}
