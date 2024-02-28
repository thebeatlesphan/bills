package com.example.bills.association;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "usergroup", path = "usergroup")
public interface UserGroupRepository extends PagingAndSortingRepository<UserGroup, Integer>, CrudRepository<UserGroup, Integer> {

}
