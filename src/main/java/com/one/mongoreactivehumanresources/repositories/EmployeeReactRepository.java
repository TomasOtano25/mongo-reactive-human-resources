package com.one.mongoreactivehumanresources.repositories;

import com.one.mongoreactivehumanresources.documents.Employee;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface EmployeeReactRepository extends ReactiveSortingRepository<Employee, String> {
}
