package com.example.bills.clan;

import com.example.bills.association.UserClan;
import com.example.bills.association.UserClanRepository;
import com.example.bills.exception.ClanNotFoundException;
import com.example.bills.exception.UserIsNotClanOwnerException;
import com.example.bills.exception.UsernameAlreadyExistsException;
import com.example.bills.expense.ExpenseRepository;
import com.example.bills.jwt.JwtService;
import com.example.bills.response.ApiResponse;
import com.example.bills.user.User;
import com.example.bills.user.UserDTO;
import com.example.bills.user.UserService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clan")
public class ClanController {
  private final ClanService clanService;
  private final ClanRepository clanRepository;
  private final UserService userService;
  private final UserClanRepository userClanRepository;
  private final JwtService jwtService;

  @Autowired
  ClanController(
      ClanService clanService,
      ClanRepository clanRepository,
      UserService userService,
      UserClanRepository userClanRepository,
      ExpenseRepository expenseRepository,
      JwtService jwtService) {
    this.clanService = clanService;
    this.clanRepository = clanRepository;
    this.userService = userService;
    this.userClanRepository = userClanRepository;
    this.jwtService = jwtService;
  }

  @PostMapping("/createClan")
  ResponseEntity<ApiResponse<?>> createClan(
      @RequestHeader("Authorization") String bearerToken,
      @RequestBody Clan clan) {
    try {
      // Verify the JWT token and extract the userId
      String userId = jwtService.getJwtUserId(bearerToken);

      // Check for duplicate clan names
      if (clanService.getClanByName(Integer.parseInt(userId), clan.getClanName()) != null)
        throw new BadRequestException("Clan name already exists");

      // Clan add logic
      Clan newClan = clanService.addClan(Integer.parseInt(userId), clan);

      Map<String, Object> data = new HashMap<>();
      data.put("clan", newClan);
      data.put("monthlyTotal", 0.00);

      return ResponseEntity
          .ok()
          .body(new ApiResponse<>("Clan successfully created", data, new Date()));
    } catch (BadRequestException ex) {
      return ResponseEntity
          .status(400)
          .body(new ApiResponse<>(ex.getMessage(), null, new Date()));
    }
  }

  // Request with GET method cannot have body
  @CrossOrigin
  @GetMapping("/getUsersFromClanId")
  // Return users from clan from given clanName
  ResponseEntity<ApiResponse<?>> getUsersFromClanId(@RequestParam String clanId) {
    List<UserDTO> users = clanService.getUsersFromClanId(Integer.parseInt(clanId));
    return ResponseEntity.ok().body(new ApiResponse<>("Users from clan retrieved", users, new Date()));
  }

  @CrossOrigin
  @GetMapping("/getClanTotalsFromUserId")
  // Return list of clans that the user has joined and their monthly budget
  ResponseEntity<ApiResponse<?>> getClanTotalsFromUserId(@RequestParam String userId) {
    // Retrieve user's clan list
    List<UserClan> userClans = clanService.getAllClansFromUserId(Integer.parseInt(userId));

    // Query the clan's monthly total
    List<Map<String, Object>> clanMonthlyTotals = clanService.getClanTotalsFromUserClan(userClans);

    return ResponseEntity.ok().body(new ApiResponse<>("Clans from user retrieved", clanMonthlyTotals, new Date()));
  };

  @PostMapping("/addUserToClan")
  ResponseEntity<ApiResponse<?>> addUserToClan(@RequestHeader("Authorization") String bearerToken,
      @RequestBody Map<String, String> request) {
    try {
      // Verify the JWT token and extract the userId
      String userId = jwtService.getJwtUserId(bearerToken);

      // Verify intended new user and clan
      User _user = userService.getUserByName(request.get("username"));
      Clan clan = clanService.getClanByName(Integer.parseInt(userId), request.get("clanName"));

      // Check if intended user already belongs to clan
      clanService.userClanExists(_user, clan);

      // Update association table
      clanService.addUserClan(_user, clan);

      UserDTO data = new UserDTO(_user.getId(), _user.getUsername());

      return ResponseEntity.ok().body(new ApiResponse<>("User added to clan", data, new Date()));
    } catch (UsernameAlreadyExistsException ex) {
      // Return an error response for the exception
      return ResponseEntity.badRequest().body(new ApiResponse<>(ex.getMessage(), null, new Date()));
    } catch (Exception ex) {
      // Return a generic error response for other exceptions
      return ResponseEntity.status(500).body(new ApiResponse<>(ex.getMessage(), null, new Date()));
    }
  }

  @DeleteMapping("/delete")
  ResponseEntity<ApiResponse<?>> deleteClan(@RequestHeader("Authorization") String bearerToken,
      @RequestParam String clanName) {
    try {
      // Verify JWT token
      String userId = jwtService.getJwtUserId(bearerToken);

      // Retrieve list of user's clans
      List<UserClan> userClanList = userClanRepository.findByUserId(Integer.parseInt(userId));
      Clan deleteClan = null;

      // Find by clanName and verify owner
      for (UserClan uc : userClanList) {
        Clan c = uc.getClan();
        if (c.getOwnerId() == Integer.parseInt(userId) && c.getClanName().equals(clanName)) {
          deleteClan = c;
          break; // Requestee is the owner of the clan
        }
      }

      // If requestee was not the clan owner
      if (deleteClan == null) {
        throw new UserIsNotClanOwnerException();
      }

      clanRepository.delete(deleteClan);
      return ResponseEntity.ok().body(new ApiResponse<>("Clan deleted", null, new Date()));
    } catch (UserIsNotClanOwnerException ex) {
      return ResponseEntity.badRequest().body(new ApiResponse<>(ex.getMessage(), null, new Date()));
    } catch (Exception ex) {
      return ResponseEntity.badRequest().body(new ApiResponse<>(ex.getMessage(), null, new Date()));
    }
  }

  @DeleteMapping("/removeMember")
  ResponseEntity<ApiResponse<?>> removeMembers(@RequestHeader("Authorization") String bearerToken,
      @RequestBody Map<String, String> request) {
    try {
      // Verify JWT token
      String userId = jwtService.getJwtUserId(bearerToken);

      // Verify clan
      List<UserClan> userClan = userClanRepository.findByUserId(Integer.parseInt(userId));
      Clan clan = null;
      for (UserClan uc : userClan) {
        if (uc.getClan().getClanName().equals(request.get("clanName"))) {
          clan = uc.getClan();
        }
      }

      // Exception if Clan is not found
      if (clan == null) {
        throw new ClanNotFoundException();
      }

      // Exception if member is the owner
      if (clan.getOwnerId() == Integer.parseInt(request.get("memberId"))) {
        throw new IllegalArgumentException("Member is the owner");
      }

      // Remove member logic
      UserClan remove = userClanRepository.findByUserIdAndClanId(Integer.parseInt(request.get("memberId")),
          clan.getId());

      if (remove == null) {
        throw new IllegalArgumentException("Selected member could not be found");
      } else {
        userClanRepository.delete(remove);
      }

      return ResponseEntity.ok().body(new ApiResponse<>("Members removed", null, new Date()));
    } catch (ClanNotFoundException ex) {
      return ResponseEntity.badRequest().body(new ApiResponse<>(ex.getMessage(), null, new Date()));
    } catch (IllegalArgumentException ex) {
      return ResponseEntity.badRequest().body(new ApiResponse<>(ex.getMessage(), null, new Date()));
    } catch (Exception ex) {
      return ResponseEntity.badRequest().body(new ApiResponse<>(ex.getMessage(), null, new Date()));
    }
  };
}
