package com.one.mongoreactivehumanresources.bussiness_controllers;

import com.one.mongoreactivehumanresources.documents.*;
import com.one.mongoreactivehumanresources.dtos.CandidateDto;
import com.one.mongoreactivehumanresources.exceptions.BadRequestException;
import com.one.mongoreactivehumanresources.exceptions.NotFoundException;
import com.one.mongoreactivehumanresources.repositories.*;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Controller
public class CandidateController {

    private CandidateReactRepository candidateReactRepository;
    private EmployeeReactRepository employeeReactRepository;
    private UserReactRepository userReactRepository;
//    private UserRepository userRepository;

    @Autowired
    public CandidateController(CandidateReactRepository candidateReactRepository,
                               UserReactRepository userReactRepository,
                               EmployeeReactRepository employeeReactRepository) {
        this.candidateReactRepository = candidateReactRepository;
        this.userReactRepository = userReactRepository;
        this.employeeReactRepository = employeeReactRepository;
    }


    // TODO: Obtener candidato del usuario actual (cambiar mensaje a no autorizado)
    public Mono<CandidateDto> readCurrent(String mobile) {
        return this.userReactRepository.findByMobile(mobile)
                .switchIfEmpty(Mono.error(new BadRequestException("Bad Request")))
                .flatMap(user -> {
                    return this.candidateReactRepository.findByUser(user)
                            .switchIfEmpty(Mono.error(new NotFoundException("Candidate mobile (" + mobile + ")")))
                            .map(CandidateDto::new);
                });
    }
    // TODO: Crear candidato
    public Mono<CandidateDto> createCandidate(String mobile, CandidateDto candidateDto) {
        Mono<Void> user;
        Candidate candidate = new Candidate();
        candidate.setName(candidateDto.getName());
        candidate.setDni(candidateDto.getDni());
        candidate.setSalary(candidateDto.getSalary());
        candidate.setTrainings(candidateDto.getTrainings());
        candidate.setWorkExperiences(candidateDto.getWorkExperiences());
        candidate.setContacts(candidateDto.getContacts());
        candidate.setJob(new Job(candidateDto.getJob()));
        candidate.setCompetencies(candidateDto.getCompetencies().stream().map(competency -> new Competency(competency)).collect(Collectors.toList()));
        candidate.setLanguages(candidateDto.getLanguages().stream().map(language -> new Language(language)).collect(Collectors.toList()));
        if(StringUtil.isNullOrEmpty(mobile)) {
            user = Mono.error(new BadRequestException("Bad Request"));
        } else {
            user = this.userReactRepository.findByMobile(mobile)
                    .switchIfEmpty(Mono.error(new NotFoundException("User mobile:" + mobile)))
                    .doOnNext(candidate::setUser)
                    .then();
        }
        return Mono.when(user).then(candidateReactRepository.save(candidate)).map(CandidateDto::new);
    }
    // TODO: Controtar candidato
    public Mono<Employee> contract(String id) {
        Employee employee = new Employee();
        Mono<Candidate> candidate = this.candidateReactRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Candidate id (" + id + ")")))
                .map(candidate1 -> {
                    candidate1.setContracted(true);
                    return candidate1;
                })
                .doOnNext(candidate1 -> {
                    employee.setName(candidate1.getName());
                    employee.setDni(candidate1.getDni());
                    employee.setSalary(candidate1.getSalary());
                    employee.setUser(candidate1.getUser());
                    employee.setJob(candidate1.getJob());
                });

        Mono<Void> candidateUpdate = this.candidateReactRepository.saveAll(candidate).then();

        return Mono.when(candidate, candidateUpdate).then(this.employeeReactRepository.save(employee));
    }

    // TODO: Listar condidatos
    public Flux<CandidateDto> readAll() {
        return this.candidateReactRepository.findAllByContractedFalse()
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
