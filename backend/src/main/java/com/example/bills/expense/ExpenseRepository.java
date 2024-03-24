package com.example.bills.expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.bills.clan.Clan;

@RepositoryRestResource(collectionResourceRel = "expense", path = "expense")
public interface ExpenseRepository
  extends PagingAndSortingRepository<Expense, Integer>, CrudRepository<Expense, Integer> {
  List<Expense> findByName(@Param("name") String name);

  List<Expense> findByAmount(@Param("amount") BigDecimal amount);

  List<Expense> findByExpenseDate(@Param("expenseDate") LocalDate expenseDate);

  List<Expense> findByClan(@Param("clan") Clan clan);

  List<Expense> findByExpenseDateAndClan(@Param("expenseDate") LocalDate expenseDate, @Param("Clan") Clan clan);

  @Query("SELECT SUM(e.amount) FROM Expense e WHERE MONTH(e.expenseDate) = :month AND YEAR(e.expenseDate) = :year AND e.clan = :clan")
  Float getTotalExpenseForMonthAndClan(@Param("month") int month, @Param("year") int year, @Param("clan") Clan clan);
}
