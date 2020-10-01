package com.one.mongoreactivehumanresources.repositories;

import com.one.mongoreactivehumanresources.documents.Candidate;
import com.one.mongoreactivehumanresources.documents.User;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface CandidateReactRepository extends ReactiveSortingRepository<Candidate, String> {
    Mono<Candidate> findByUser(User user);
}
