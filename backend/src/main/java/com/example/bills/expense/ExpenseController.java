package com.example.bills.expense;

import com.example.bills.clan.Clan;
import com.example.bills.clan.ClanService;
import com.example.bills.exception.ClanNotFoundException;
import com.example.bills.jwt.JwtService;
import com.example.bills.response.ApiResponse;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
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
  private final ClanService clanService;
  private final ExpenseService expenseService;
  private final JwtService jwtService;

  @Autowired
  ExpenseController(
      ClanService clanService,
      ExpenseService expenseService,
      ExpenseRepository expenseRepository,
      JwtService jwtService) {
    this.clanService = clanService;
    this.expenseService = expenseService;
    this.jwtService = jwtService;
  }

  @PostMapping("/add")
  ResponseEntity<ApiResponse<?>> addExpense(
      @RequestHeader("Authorization") String bearerToken,
      @RequestBody Map<String, String> request) {
    try {
      // Verify the JWT token and extract the userId
      String userId = jwtService.getJwtUserId(bearerToken);

      Clan clan = clanService.getClanByName(Integer.parseInt(userId), request.get("clanName"));

      // Handle if clan is not found
      if (clan == null) {
        throw new ClanNotFoundException();
      }

      // Expense add logic
      Expense newExpense = expenseService.addExpense(request.get("expense"), request.get("amount"),
          request.get("expenseDate"), clan);

      Map<String, Object> data = new HashMap<>();
      data.put("expense", newExpense);

      return ResponseEntity.ok().body(new ApiResponse<>("Expense successfully added", data, new Date()));
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
      String userId = jwtService.getJwtUserId(bearerToken);

      Clan clan = clanService.getClanByName(Integer.parseInt(userId), clanName);

      // Handle if clan is not found
      if (clan == null) {
        throw new ClanNotFoundException();
      }

      // Clan expenses logic
      List<Expense> clanExpenses = expenseService.getExpenseByClan(clan);

      // Sort the list by expenseDate desc
      Collections.sort(clanExpenses, Comparator.comparing(Expense::getExpenseDate).reversed());
      return ResponseEntity.ok().body(new ApiResponse<>("Retrieved clan expenses.", clanExpenses, new Date()));
    } catch (Exception ex) {
      return ResponseEntity.badRequest().body(new ApiResponse<>(ex.getMessage(), null, new Date()));
    }
  }

  @PostMapping("/delete")
  ResponseEntity<ApiResponse<?>> deleteExpense(@RequestHeader("Authorization") String bearerToken,
      @RequestBody Map<String, String> request) {
    try {
      // Verify JWT
      jwtService.getJwtUserId(bearerToken);

      String expenseId = request.get("expenseId");
      expenseService.deleteByExpenseId(Integer.parseInt(expenseId));
      return ResponseEntity.ok().body(new ApiResponse<>("Expense has been deleted!", null, new Date()));
    } catch (Exception ex) {
      return ResponseEntity.ok().body(new ApiResponse<>(ex.getMessage(), null, new Date()));
    }

  }
}