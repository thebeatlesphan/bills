package com.example.bills.clan;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "clan", path = "clan")
public interface ClanRepository extends PagingAndSortingRepository<Clan, Integer>, CrudRepository<Clan, Integer> {

    Clan findByClanName(@Param("clanName") String clanName);

    @Query("Select c from Clan c WHERE c.ownerId = :ownerId")
    List<Clan> findByOwnerId(@Param("ownerId") Integer ownerId);
}
