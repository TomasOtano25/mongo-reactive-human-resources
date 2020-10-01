package com.one.mongoreactivehumanresources.bussiness_controllers;

import com.one.mongoreactivehumanresources.documents.Job;
import com.one.mongoreactivehumanresources.dtos.JobDto;
import com.one.mongoreactivehumanresources.exceptions.BadRequestException;
import com.one.mongoreactivehumanresources.exceptions.NotFoundException;
import com.one.mongoreactivehumanresources.repositories.JobReactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class JobController {
    private JobReactRepository jobReactRepository;

    @Autowired
    public JobController(JobReactRepository jobReactRepository) {
        this.jobReactRepository = jobReactRepository;
    }

    public Flux<JobDto> readAll() {
        return this.jobReactRepository.findAll()
                .switchIfEmpty(Flux.error(new BadRequestException("Bad Request")))
                .map(JobDto::new);
    }

    public Mono<Job> createJob(JobDto jobDto) {
        Job job = new Job();
        job.setName(jobDto.getName());
        job.setRisk(jobDto.getRisk());
        job.setMinSalary(jobDto.getMinSalary());
        job.setMaxSalary(jobDto.getMaxSalary());
        return jobReactRepository.save(job);
    }

    public Mono<Void> deleteJob(String id) {
        Mono<Job> job = this.jobReactRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Job id:" + id )));
        return Mono
                .when(job)
                .then(this.jobReactRepository.deleteById(id));
    }

    public Mono<JobDto> updateJob(String id, JobDto jobDto) {
        Mono<Job> job = this.jobReactRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Job id:" + id )))
                .map(job1 -> {
                    job1.setName(jobDto.getName());
                    job1.setRisk(jobDto.getRisk());
                    job1.setMinSalary(jobDto.getMinSalary());
                    job1.setMaxSalary(jobDto.getMaxSalary());
                    return job1;
                });
        return Mono.when(job).then(this.jobReactRepository.saveAll(job).next().map(JobDto::new));
    }

    public Mono<JobDto> readOne(String id) {
        return this.jobReactRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Job id:" + id )))
                .map(JobDto::new);
    }
}
