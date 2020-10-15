package com.one.mongoreactivehumanresources.dtos.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSearchDto {
    private LocalDateTime from;
    private LocalDate to;
}
