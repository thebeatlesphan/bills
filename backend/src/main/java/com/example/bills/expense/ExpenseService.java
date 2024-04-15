package com.example.bills.expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bills.clan.Clan;

import jakarta.transaction.Transactional;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense addExpense(String name, String amount, String date, Clan clan) {
        BigDecimal _amount = new BigDecimal(amount);
        LocalDate _date = LocalDate.parse(date);
        Expense newExpense = new Expense(name, _amount, _date);
        newExpense.setClan(clan);
        expenseRepository.save(newExpense);
        return newExpense;
    }

    public List<Expense> getExpenseByClan(Clan clan) {
        return expenseRepository.findByClan(clan);
    }

    @Transactional
    public void deleteByExpenseId(Integer expenseId) {
        expenseRepository.deleteById(expenseId);
    }
}
