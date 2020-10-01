package com.one.mongoreactivehumanresources.bussiness_controllers;

import com.one.mongoreactivehumanresources.documents.Competency;
import com.one.mongoreactivehumanresources.dtos.CompetencyDto;
import com.one.mongoreactivehumanresources.exceptions.BadRequestException;
import com.one.mongoreactivehumanresources.exceptions.NotFoundException;
import com.one.mongoreactivehumanresources.repositories.CompetencyReactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CompetencyController {
    private CompetencyReactRepository competencyReactRepository;

    @Autowired
    public CompetencyController(CompetencyReactRepository competencyReactRepository) {
        this.competencyReactRepository = competencyReactRepository;
    }

    public Flux<CompetencyDto> readAll() {
        return this.competencyReactRepository.findAll()
                .switchIfEmpty(Flux.error(new BadRequestException("Bad Request")))
                .map(CompetencyDto::new);
    }

    public Mono<Competency> createCompetency(CompetencyDto competencyDto) {
        Competency competency = new Competency();
        competency.setDescription(competencyDto.getDescription());
        return competencyReactRepository.save(competency);
    }

    public Mono<Void> deleteCompetency(String id) {
        Mono<Competency> competency = this.competencyReactRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Competency id:" + id )));
        return Mono
                .when(competency)
                .then(this.competencyReactRepository.deleteById(id));
    }

    public Mono<CompetencyDto> updateCompetency(String id, CompetencyDto competencyDto) {
        Mono<Competency> competency = this.competencyReactRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Competency id:" + id )))
                .map(competency1 -> {
                    competency1.setDescription(competencyDto.getDescription());
                    return competency1;
                });
        return Mono.when(competency).then(this.competencyReactRepository.saveAll(competency).next().map(CompetencyDto::new));
    }

    public Mono<CompetencyDto> readOne(String id) {
        return this.competencyReactRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Competency id:" + id )))
                .map(CompetencyDto::new);
    }
}
