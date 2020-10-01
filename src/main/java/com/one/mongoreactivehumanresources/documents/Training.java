package com.one.mongoreactivehumanresources.documents;

import com.one.mongoreactivehumanresources.documents.enums.Level;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Training {
    private String description;
    private Level level;
    private LocalDate from;
    private LocalDate to;
    private String institution;
}
