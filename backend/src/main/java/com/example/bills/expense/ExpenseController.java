package com.example.bills.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bills.association.ExpenseGroupRepository;
import com.example.bills.jwt.JwtTokenProvider;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
    private final ExpenseRepository expenseRepository;
    private final ExpenseGroupRepository expenseGroupRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    ExpenseController(ExpenseRepository expenseRepository, ExpenseGroupRepository expenseGroupRepository,
            JwtTokenProvider jwtTokenProvider) {
        this.expenseRepository = expenseRepository;
        this.expenseGroupRepository = expenseGroupRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/add")
    String addExpense(@RequestHeader("Authorization") String bearerToken, @RequestBody Expense expense) {
        // Verify the JWT token and extract the userId
        String jwtToken = bearerToken.substring(7);
        String userId = jwtTokenProvider.getUsernameFromToken(jwtToken).getPayload().getSubject();

        // Expense add logic
        Expense newExpense = expense;
        

        return "Hi";
    }
}
