package com.example.bills.association;

import com.example.bills.clan.Clan;
import com.example.bills.expense.Expense;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "expensegroup", path = "expensegroup")
public interface ExpenseClanRepository
  extends
    PagingAndSortingRepository<ExpenseClan, Integer>,
    CrudRepository<ExpenseClan, Integer> {
  List<ExpenseClan> findByClan(@Param("clan") Clan clan);

  List<ExpenseClan> findByExpense(@Param("expense") Expense expense);
}
