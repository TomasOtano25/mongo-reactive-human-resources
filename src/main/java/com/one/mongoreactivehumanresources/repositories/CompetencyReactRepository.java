package com.one.mongoreactivehumanresources.repositories;

import com.one.mongoreactivehumanresources.documents.Competency;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface CompetencyReactRepository extends ReactiveSortingRepository<Competency, String> {
}
