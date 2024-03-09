package com.example.bills.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository
  extends PagingAndSortingRepository<User, Integer>, CrudRepository<User, Integer> {
  User findByUsername(@Param("username") String username);
}
