package com.example.bills.expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "expense", path = "expense")
public interface ExpenseRepository extends PagingAndSortingRepository<Expense, Integer>, CrudRepository<Expense, Integer> {

    List<Expense> findByName(@Param("name") String name);

    List<Expense> findByAmount(@Param("amount") BigDecimal amount);

    List<Expense> findByExpenseDate(@Param("expenseDate") LocalDate expenseDate);
}