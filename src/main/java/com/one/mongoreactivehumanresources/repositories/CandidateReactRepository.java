package com.one.mongoreactivehumanresources.repositories;

import com.one.mongoreactivehumanresources.documents.Candidate;
import com.one.mongoreactivehumanresources.documents.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CandidateReactRepository extends ReactiveSortingRepository<Candidate, String> {
    Mono<Candidate> findByUser(User user);
    Flux<Candidate> findAllByContractedFalse();
//    @Query("{ contracted: ?0 }")
//    Flux<Candidate> findAllNotContracted(Boolean contracted);
//    Mono<Candidate> findByUserMobile(String mobile);
}
