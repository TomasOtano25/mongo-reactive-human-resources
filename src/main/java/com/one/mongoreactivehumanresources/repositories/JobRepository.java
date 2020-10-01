package com.one.mongoreactivehumanresources.repositories;

import com.one.mongoreactivehumanresources.documents.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {
}
