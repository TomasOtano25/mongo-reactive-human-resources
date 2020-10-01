package com.one.mongoreactivehumanresources.repositories;

import com.one.mongoreactivehumanresources.documents.Job;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface JobReactRepository extends ReactiveSortingRepository<Job, String> {
}
