package com.one.mongoreactivehumanresources.repositories;

import com.one.mongoreactivehumanresources.documents.Candidate;
import com.one.mongoreactivehumanresources.documents.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CandidateRepository extends MongoRepository<Candidate, String> {
    Candidate getByUser(User user);
}
