package com.one.mongoreactivehumanresources.api_rest_controllers;

import com.one.mongoreactivehumanresources.bussiness_controllers.LanguageController;
import com.one.mongoreactivehumanresources.documents.Language;
import com.one.mongoreactivehumanresources.dtos.LanguageDto;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


@RestController
@RequestMapping(LanguageResource.LANGUAGES)
public class LanguageResource {
    public static final String LANGUAGES = "/languages";
    public static final String LANGUAGE_ID = "/{id}";

    private LanguageController languageController;

    @Autowired
    public LanguageResource(LanguageController languageController) {
        this.languageController = languageController;
    }

    @GetMapping
    public Flux<LanguageDto> readAll() {
        return this.languageController.readAll()
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @GetMapping(value = LANGUAGE_ID)
    public Mono<LanguageDto> readOne(@PathVariable String id) {
        return this.languageController.readOne(id)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @PostMapping(produces = {"application/json"})
    public Mono<Language> create(@Valid @RequestBody LanguageDto languageDto) {
        return this.languageController.createLanguage(languageDto)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @PutMapping(value = LANGUAGE_ID, produces = {"application/json"})
    public Mono<LanguageDto> update(@PathVariable String id, @Valid @RequestBody LanguageDto languageDto) {
        return this.languageController.updateLanguage(id, languageDto)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }

    @DeleteMapping(value = LANGUAGE_ID)
    public Mono<Void> delete(@PathVariable String id) {
        return this.languageController.deleteLanguage(id)
                .doOnNext(log -> LogManager.getLogger(this.getClass()).debug(log));
    }
}
