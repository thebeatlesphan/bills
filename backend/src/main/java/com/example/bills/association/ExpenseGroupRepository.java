package com.example.bills.association;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.bills.clan.Clan;
import com.example.bills.expense.Expense;

@RepositoryRestResource(collectionResourceRel = "expensegroup", path = "expensegroup")
public interface ExpenseGroupRepository extends PagingAndSortingRepository<ExpenseGroup, Integer>, CrudRepository<ExpenseGroup, Integer> {
   
    List<ExpenseGroup> findByClan(@Param("clan") Clan clan);

    List<ExpenseGroup> findByExpense(@Param("expense") Expense expense);
}
