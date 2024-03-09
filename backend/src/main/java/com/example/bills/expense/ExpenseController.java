package com.example.bills.expense;

import com.example.bills.association.ExpenseClan;
import com.example.bills.association.ExpenseClanRepository;
import com.example.bills.clan.Clan;
import com.example.bills.clan.ClanRepository;
import com.example.bills.jwt.JwtTokenProvider;
import com.example.bills.response.ApiResponse;
import com.example.bills.user.User;
import com.example.bills.user.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
  ;
  private final UserRepository userRepository;
  private final ExpenseRepository expenseRepository;
  private final ExpenseClanRepository expenseGroupRepository;
  private final ClanRepository clanRepository;
  private final JwtTokenProvider jwtTokenProvider;

  @Autowired
  ExpenseController(
      UserRepository userRepository,
      ExpenseRepository expenseRepository,
      ExpenseClanRepository expenseGroupRepository,
      ClanRepository clanRepository,
      JwtTokenProvider jwtTokenProvider) {
    this.userRepository = userRepository;
    this.expenseRepository = expenseRepository;
    this.expenseGroupRepository = expenseGroupRepository;
    this.clanRepository = clanRepository;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @PostMapping("/add")
  ResponseEntity<ApiResponse<?>> addExpense(
      @RequestHeader("Authorization") String bearerToken,
      @RequestBody Map<String, String> request) {
    // Verify the JWT token and extract the userId
    String jwtToken = bearerToken.substring(7);
    String userId = jwtTokenProvider
        .getUsernameFromToken(jwtToken)
        .getPayload()
        .getSubject();

    // Expense add logic
    Expense newExpense = new Expense(request.get("expense"), new BigDecimal(request.get("amount")),
        LocalDate.parse(request.get("expenseDate")));
    expenseRepository.save(newExpense);

    // ExpenseClan association logic
    List<Clan> userClans = clanRepository.findByOwnerId(Integer.parseInt(userId));
    Clan clan = null;
    for (Clan c : userClans) {
      if (c.getClanName().equals(request.get("clanName"))) {
        clan = c;
      }
    }
    ExpenseClan newExpenseClan = new ExpenseClan(clan, newExpense);
    expenseGroupRepository.save(newExpenseClan);

    return ResponseEntity.ok().body(new ApiResponse<>("Expense successfully added", null, new Date()));
  }
}
