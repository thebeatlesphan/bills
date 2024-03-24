package com.example.bills.expense;

import com.example.bills.association.UserClan;
import com.example.bills.association.UserClanRepository;
import com.example.bills.clan.Clan;
import com.example.bills.exception.ClanNotFoundException;
import com.example.bills.jwt.JwtTokenProvider;
import com.example.bills.response.ApiResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
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
  private final UserClanRepository userClanRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final ExpenseRepository expenseRepository;

  @Autowired
  ExpenseController(
      ExpenseRepository expenseRepository,
      UserClanRepository userClanRepository,
      JwtTokenProvider jwtTokenProvider) {
    this.expenseRepository = expenseRepository;
    this.userClanRepository = userClanRepository;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @PostMapping("/add")
  ResponseEntity<ApiResponse<?>> addExpense(
      @RequestHeader("Authorization") String bearerToken,
      @RequestBody Map<String, String> request) {
    try {

      // Verify the JWT token and extract the userId
      String jwtToken = bearerToken.substring(7);
      String userId = jwtTokenProvider
          .getUsernameFromToken(jwtToken)
          .getPayload()
          .getSubject();

      // Find user's clan by name
      List<UserClan> userClans = userClanRepository.findByUserId(Integer.parseInt(userId));
      Clan clan = null;
      for (UserClan uc : userClans) {
        if (uc.getClan().getClanName().equals(request.get("clanName"))) {
          clan = uc.getClan();
        }
      }

      if (clan == null) {
        // Handle if clan is not found
        throw new ClanNotFoundException();
      }

      // Expense add logic
      Expense newExpense = new Expense(request.get("expense"), new BigDecimal(request.get("amount")),
          LocalDate.parse(request.get("expenseDate")));
      newExpense.setClan(clan);
      expenseRepository.save(newExpense);

      return ResponseEntity.ok().body(new ApiResponse<>("Expense successfully added", null, new Date()));
    } catch (ClanNotFoundException ex) {
      return ResponseEntity.badRequest().body(new ApiResponse<>(ex.getMessage(), null, new Date()));
    } catch (Exception ex) {
      return ResponseEntity.badRequest().body(new ApiResponse<>(ex.getMessage(), null, new Date()));
    }
  }

  @CrossOrigin
  @GetMapping("/getClanExpenses")
  ResponseEntity<ApiResponse<?>> getClanExpenses(@RequestHeader("Authorization") String bearerToken,
      @RequestParam String clanName) {
    try {

      // Verify JWT token
      String jwtToken = bearerToken.substring(7);
      String userId = jwtTokenProvider
          .getUsernameFromToken(jwtToken)
          .getPayload()
          .getSubject();

      // Find clan from userId and clanName
      List<UserClan> userClan = userClanRepository.findByUserId(Integer.parseInt(userId));
      Clan clan = null;
      for (UserClan uc : userClan) {
        if (uc.getClan().getClanName().equals(clanName)) {
          clan = uc.getClan();
        }
      }

      // Handle if clan is not found
      if (clan == null) {
        throw new ClanNotFoundException();
      }

      // Clan expenses logic
      List<Expense> clanExpenses = expenseRepository.findByClan(clan);

      // Sort the list by expenseDate desc
      Collections.sort(clanExpenses, Comparator.comparing(Expense::getExpenseDate).reversed());
      return ResponseEntity.ok().body(new ApiResponse<>("Retrieved clan expenses.", clanExpenses, new Date()));
    } catch (Exception ex) {
      return ResponseEntity.badRequest().body(new ApiResponse<>(ex.getMessage(), null, new Date()));
    }
  }
}
