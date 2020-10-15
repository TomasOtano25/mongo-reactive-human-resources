package com.one.mongoreactivehumanresources.api_rest_controllers;

import com.one.mongoreactivehumanresources.bussiness_controllers.CandidateController;
import com.one.mongoreactivehumanresources.documents.Employee;
import com.one.mongoreactivehumanresources.dtos.CandidateDto;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(CandidateResource.CANDIDATES)
public class CandidateResource {
    public static final String CANDIDATES = "/candidates";
    public static final String CANDIDATE_CURRENT = "/current";
    public static final String CANDIDATES_ID = "/{id}";

    private CandidateController candidateController;

    @Autowired
    public CandidateResource(CandidateController candidateController) {
        this.candidateController = candidateController;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping(produces = {"application/json"})
    public Mono<CandidateDto> create(Principal principal, @Valid @RequestBody CandidateDto candidateDto) {
        return this.candidateController.createCandidate(principal.getName(), candidateDto);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping(value = CANDIDATE_CURRENT)
    public Mono<CandidateDto> readCurrent(Principal principal) {
        return this.candidateController.readCurrent(principal.getName());
    }

    @GetMapping
    public Flux<CandidateDto> readAll() {
        return this.candidateController.readAll()
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @PostMapping(value = CANDIDATES_ID)
    public Mono<Employee> contract(@PathVariable String id) {
        return this.candidateController.contract(id);
    }

}
