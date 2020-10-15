package com.one.mongoreactivehumanresources.bussiness_controllers;

import com.one.mongoreactivehumanresources.bussiness_services.PdfService;
import com.one.mongoreactivehumanresources.documents.Employee;
import com.one.mongoreactivehumanresources.exceptions.BadRequestException;
import com.one.mongoreactivehumanresources.exceptions.NotFoundException;
import com.one.mongoreactivehumanresources.repositories.EmployeeReactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Controller
public class EmployeeController {
    private EmployeeReactRepository employeeReactRepository;
    private PdfService pdfService;

    @Autowired
    public EmployeeController(EmployeeReactRepository employeeReactRepository,
                              PdfService pdfService) {
        this.employeeReactRepository = employeeReactRepository;
        this.pdfService = pdfService;
    }

    public Flux<Employee> readAll() {
        return this.employeeReactRepository.findAll()
                .switchIfEmpty(Flux.error(new BadRequestException("Bad Request")));
    }

    public Flux<Employee> readAllByFilters(LocalDate fromDate, LocalDate toDate) {
        return employeeReactRepository.findAll()
                .filter(employee ->
                        ((fromDate == null || employee.getRegisterDate().toLocalDate().compareTo(fromDate) >= 0)
                                && (toDate == null || employee.getRegisterDate().toLocalDate().compareTo(toDate) <= 0))
                );
    }

    @Transactional
    public Mono<byte[]> printEmployee(String id) {
        Mono<Employee> employeeReact = employeeReactRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Employee code (" + id + ")")));

        return pdfService.generateEmployee(employeeReact);
    }
}
