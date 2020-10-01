package com.one.mongoreactivehumanresources.api_rest_controllers;

import com.one.mongoreactivehumanresources.bussiness_controllers.JobController;
import com.one.mongoreactivehumanresources.documents.Job;
import com.one.mongoreactivehumanresources.dtos.JobDto;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(JobResource.JOBS)
public class JobResource {
    public static final String JOBS = "/jobs";
    public static final String JOB_ID = "/{id}";

    private JobController jobController;

    @Autowired
    public JobResource(JobController jobController) {
        this.jobController = jobController;
    }

    @GetMapping
    public Flux<JobDto> readAll() {
        return this.jobController.readAll()
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @GetMapping(value = JOB_ID)
    public Mono<JobDto> readOne(@PathVariable String id) {
        return this.jobController.readOne(id)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @PostMapping(produces = {"application/json"})
    public Mono<Job> create(@Valid @RequestBody JobDto jobDto) {
        return this.jobController.createJob(jobDto)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @PutMapping(value = JOB_ID, produces = {"application/json"})
    public Mono<JobDto> update(@PathVariable String id, @Valid @RequestBody JobDto jobDto) {
        return this.jobController.updateJob(id, jobDto)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @DeleteMapping(value = JOB_ID)
    public Mono<Void> delete(@PathVariable String id) {
        return this.jobController.deleteJob(id)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }
}
