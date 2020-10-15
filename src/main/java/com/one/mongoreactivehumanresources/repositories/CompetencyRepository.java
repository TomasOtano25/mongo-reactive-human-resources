package com.one.mongoreactivehumanresources.repositories;

import com.one.mongoreactivehumanresources.documents.Competency;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompetencyRepository extends MongoRepository<Competency, String> {
    Competency findByDescription(String description);
}
