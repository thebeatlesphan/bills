package com.example.bills.association;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "userclan", path = "userclan")
public interface UserClanRepository
    extends
    PagingAndSortingRepository<UserClan, Integer>, CrudRepository<UserClan, Integer> {

  List<UserClan> findByClanId(@Param("clan_id") Integer clanId);

  List<UserClan> findByUserId(@Param("user_id") Integer userId);

  UserClan findByUserIdAndClanId(@Param("user_id") Integer userId, @Param("clan_id") Integer clanId);
}
