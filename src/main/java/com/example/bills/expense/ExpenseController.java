package com.example.bills.expense;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/expense")
public class ExpenseController {
    @Autowired
    private ExpenseRepository expenseRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewExpense(
            @RequestParam String name,
            @RequestParam String amount,
            @RequestParam String date) {

        Expense n = new Expense();
        n.setName(name);
        n.setAmount(new BigDecimal(amount));
        LocalDate expenseDate = LocalDate.parse(date);
        n.setExpenseDate(expenseDate);
        expenseRepository.save(n);
        return "Expense added successfully";
    }
}
