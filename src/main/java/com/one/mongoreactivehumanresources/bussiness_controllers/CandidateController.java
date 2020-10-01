package com.one.mongoreactivehumanresources.bussiness_controllers;

import com.one.mongoreactivehumanresources.documents.Candidate;
import com.one.mongoreactivehumanresources.dtos.CandidateDto;
import com.one.mongoreactivehumanresources.exceptions.BadRequestException;
import com.one.mongoreactivehumanresources.exceptions.NotFoundException;
import com.one.mongoreactivehumanresources.repositories.CandidateReactRepository;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CandidateController {

    private CandidateReactRepository candidateReactRepository;

    // TODO: Obtener candidato del usuario actual
    // TODO: Crear candidato

    // TODO: Controtar candidato
    // TODO: Listar condidatos
    public Flux<CandidateDto> readAll() {
        return this.candidateReactRepository.findAll()
                .switchIfEmpty(Flux.error(new BadRequestException("Bad Request")))
                .map(CandidateDto::new);
    }
    // TODO: Editar candidatos
    public Mono<CandidateDto> updateCandidate(String id, CandidateDto candidateDto) {
        Mono<Candidate> candidate = this.candidateReactRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Candidate id:" + id)))
                .map(candidate1 -> {
                    candidate1.setName(candidateDto.getName());
                    candidate1.setDni(candidateDto.getDni());
                    candidate1.setSalary(candidateDto.getSalary());
                    candidate1.setTrainings(candidateDto.getTrainings());
                    return candidate1;
                });

        return Mono.when(candidate).then(this.candidateReactRepository.saveAll(candidate).next()).map(CandidateDto::new);
    }
    // TODO: Obtener un condidato
    public Mono<CandidateDto> readOne(String id) {
        return this.candidateReactRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Candidate id (" + id + ")")))
                .map(CandidateDto::new);
    }
    // TODO: Eliminar candidato
    public Mono<Void> deleteCandidate(String id) {
        Mono<Candidate> candidate = this.candidateReactRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Candidate id:" + id )));

        return Mono.when(candidate).then(this.candidateReactRepository.deleteById(id));
    }

}
