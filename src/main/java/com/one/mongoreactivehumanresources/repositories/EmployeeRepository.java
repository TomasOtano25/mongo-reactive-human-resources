package com.one.mongoreactivehumanresources.repositories;

import com.one.mongoreactivehumanresources.documents.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
}
