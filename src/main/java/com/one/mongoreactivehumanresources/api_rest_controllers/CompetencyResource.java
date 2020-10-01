package com.one.mongoreactivehumanresources.api_rest_controllers;

import com.one.mongoreactivehumanresources.bussiness_controllers.CompetencyController;
import com.one.mongoreactivehumanresources.documents.Competency;
import com.one.mongoreactivehumanresources.dtos.CompetencyDto;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(CompetencyResource.COMPETENCIES)
public class CompetencyResource {
    public static final String COMPETENCIES = "/competencies";
    public static final String COMPETENCY_ID = "/{id}";

    private CompetencyController competencyController;

    @Autowired
    public CompetencyResource(CompetencyController competencyController) {
        this.competencyController = competencyController;
    }

    @GetMapping
    public Flux<CompetencyDto> readAll() {
        return this.competencyController.readAll()
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @GetMapping(value = COMPETENCY_ID)
    public Mono<CompetencyDto> readOne(@PathVariable String id) {
        return this.competencyController.readOne(id)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @PostMapping(produces = {"application/json"})
    public Mono<Competency> create(@Valid @RequestBody CompetencyDto competencyDto) {
        return this.competencyController.createCompetency(competencyDto)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @PutMapping(value = COMPETENCY_ID, produces = {"application/json"})
    public Mono<CompetencyDto> update(@PathVariable String id, @Valid @RequestBody CompetencyDto competencyDto) {
        return this.competencyController.updateCompetency(id, competencyDto)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @DeleteMapping(value = COMPETENCY_ID)
    public Mono<Void> delete(@PathVariable String id) {
        return this.competencyController.deleteCompetency(id)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

}
