package com.one.mongoreactivehumanresources.documents;

import com.one.mongoreactivehumanresources.dtos.LanguageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode
@ToString
@Data
@NoArgsConstructor
@Document
public class Language {
    @Id
    private String id;

    private String name;

    public Language(String name) {
        this.name = name;
    }

    public Language(LanguageDto languageDto) {
        this.id = languageDto.getId();
        this.name = languageDto.getName();
    }
}
