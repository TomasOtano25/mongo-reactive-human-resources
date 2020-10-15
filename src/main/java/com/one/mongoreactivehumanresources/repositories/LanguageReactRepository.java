package com.one.mongoreactivehumanresources.repositories;

import com.one.mongoreactivehumanresources.documents.Language;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LanguageReactRepository extends ReactiveSortingRepository<Language, String> {
    Mono<Language> findByName(String languageName);

    Flux<Language> findByNameLike(String name);
}
