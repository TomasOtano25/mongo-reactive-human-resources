package com.one.mongoreactivehumanresources.api_rest_controllers;

import com.one.mongoreactivehumanresources.bussiness_controllers.EmployeeController;
import com.one.mongoreactivehumanresources.documents.Employee;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping(EmployeeResource.EMPLOYEES)
public class EmployeeResource {
    public static final String EMPLOYEES = "/employees";
    public static final String SEARCH = "/search";
    public static final String EMPLOYEE_ID = "/{id}";
    public static final String PRINT = "/print";

    private EmployeeController employeeController;

    @Autowired
    public EmployeeResource(EmployeeController employeeController) {
        this.employeeController = employeeController;
    }

    @GetMapping
    public Flux<Employee> readAll() {
        return this.employeeController.readAll()
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @GetMapping(value = SEARCH)
    public Flux<Employee> search(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate ,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        System.out.println(fromDate);
        System.out.println(toDate);
        return this.employeeController.readAllByFilters(fromDate, toDate)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @PostMapping(value = EMPLOYEE_ID + PRINT, produces = {"application/pdf"})
    public Mono<byte[]> print(@PathVariable String id) {
        return this.employeeController.printEmployee(id)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }
}
