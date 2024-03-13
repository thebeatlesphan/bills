package com.example.bills.expense;

import com.example.bills.association.ExpenseClan;
import com.example.bills.association.ExpenseClanRepository;
import com.example.bills.association.UserClan;
import com.example.bills.association.UserClanRepository;
import com.example.bills.clan.Clan;
import com.example.bills.clan.ClanRepository;
import com.example.bills.jwt.JwtTokenProvider;
import com.example.bills.response.ApiResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
  private final ExpenseRepository expenseRepository;
  private final ExpenseClanRepository expenseClanRepository;
  private final ClanRepository clanRepository;
  private final UserClanRepository userClanRepository;
  private final JwtTokenProvider jwtTokenProvider;

  @Autowired
  ExpenseController(
      ExpenseRepository expenseRepository,
      ExpenseClanRepository expenseClanRepository,
      ClanRepository clanRepository,
      UserClanRepository userClanRepository,
      JwtTokenProvider jwtTokenProvider) {
    this.expenseRepository = expenseRepository;
    this.expenseClanRepository = expenseClanRepository;
    this.clanRepository = clanRepository;
    this.userClanRepository = userClanRepository;
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
    expenseClanRepository.save(newExpenseClan);

    return ResponseEntity.ok().body(new ApiResponse<>("Expense successfully added", null, new Date()));
  }

  @CrossOrigin
  @GetMapping("/getClanExpenses")
  ResponseEntity<ApiResponse<?>> getClanExpenses(@RequestHeader("Authorization") String bearerToken,
      @RequestParam String clanName) {
    // Verify JWT token
    String jwtToken = bearerToken.substring(7);
    String userId = jwtTokenProvider
        .getUsernameFromToken(jwtToken)
        .getPayload()
        .getSubject();

    // Clan expenses logic
    // Find id of param ClanName from user's clan list
    List<UserClan> clanList = userClanRepository.findByUserId(Integer.parseInt(userId));
    Clan clan = null;
    for (UserClan uc : clanList) {
      Clan c = uc.getClan();
      if (c.getClanName().equals(clanName)) {
        clan = c;
      }
    }

    // Return expenses from the found clan
    List<Expense> clanExpenses = new ArrayList<>();
    List<ExpenseClan> expenses = expenseClanRepository.findByClan(clan);
    for (ExpenseClan ec : expenses) {
      Expense e = ec.getExpense();
      clanExpenses.add(e);
    }

    // Sort the list by expenseDate desc
    Collections.sort(clanExpenses, Comparator.comparing(Expense::getExpenseDate).reversed());
    return ResponseEntity.ok().body(new ApiResponse<>("Retrieved clan expenses.", clanExpenses, new Date()));
  }
}
