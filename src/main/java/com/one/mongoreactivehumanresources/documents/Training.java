package com.one.mongoreactivehumanresources.documents;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.one.mongoreactivehumanresources.documents.enums.Level;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Training {
    private String description;
    private Level level;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime from;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime to;
    private String institution;
}
