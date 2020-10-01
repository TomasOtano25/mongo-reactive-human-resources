package com.one.mongoreactivehumanresources.repositories;

import com.one.mongoreactivehumanresources.documents.Language;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LanguageRepository extends MongoRepository<Language, String> {
}
