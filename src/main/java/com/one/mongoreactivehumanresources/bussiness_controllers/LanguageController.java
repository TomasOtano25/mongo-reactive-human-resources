package com.one.mongoreactivehumanresources.bussiness_controllers;

import com.one.mongoreactivehumanresources.documents.Language;
import com.one.mongoreactivehumanresources.dtos.LanguageDto;
import com.one.mongoreactivehumanresources.exceptions.BadRequestException;
import com.one.mongoreactivehumanresources.exceptions.NotFoundException;
import com.one.mongoreactivehumanresources.repositories.LanguageReactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class LanguageController {
    private LanguageReactRepository languageReactRepository;

    @Autowired
    public LanguageController(LanguageReactRepository languageReactRepository) {
        this.languageReactRepository = languageReactRepository;
    }

    public Flux<LanguageDto> readAll() {
        return this.languageReactRepository.findAll()
                .switchIfEmpty(Flux.error(new BadRequestException("Bad Request")))
                .map(LanguageDto::new);
    }

    public Mono<Language> createLanguage(LanguageDto languageDto) {
        Language language = new Language();
        language.setName(languageDto.getName());
        return languageReactRepository.save(language);
    }

    public Mono<Void> deleteLanguage(String id) {
        Mono<Language> language = this.languageReactRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Language id:" + id )));
        return Mono
                .when(language)
                .then(this.languageReactRepository.deleteById(id));
    }

    public Mono<LanguageDto> updateLanguage(String id, LanguageDto languageDto) {
        Mono<Language> language = this.languageReactRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Language id:" + id )))
                .map(language1 -> {
                   language1.setName(languageDto.getName());
                   return language1;
                });
        return Mono.when(language).then(this.languageReactRepository.saveAll(language).next().map(LanguageDto::new));
    }

    public Mono<LanguageDto> readOne(String id) {
        return this.languageReactRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Language id:" + id )))
                .map(LanguageDto::new);
    }
}
