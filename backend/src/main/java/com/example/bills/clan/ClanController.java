package com.example.bills.clan;

import com.example.bills.association.UserClan;
import com.example.bills.association.UserClanRepository;
import com.example.bills.exception.ClanNotFoundException;
import com.example.bills.exception.UserIsNotClanOwnerException;
import com.example.bills.exception.UsernameAlreadyExistsException;
import com.example.bills.jwt.JwtTokenProvider;
import com.example.bills.response.ApiResponse;
import com.example.bills.user.User;
import com.example.bills.user.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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
  private final UserRepository userRepository;
  private final ClanRepository clanRepository;
  private final UserClanRepository userClanRepository;
  private final JwtTokenProvider jwtTokenProvider;

  @Autowired
  ClanController(
      UserRepository userRepository,
      ClanRepository clanRepository,
      UserClanRepository userClanRepository,
      JwtTokenProvider jwtTokenProvider) {
    this.userRepository = userRepository;
    this.clanRepository = clanRepository;
    this.userClanRepository = userClanRepository;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @PostMapping("/add")
  ResponseEntity<ApiResponse<Clan>> registerGroup(
      @RequestHeader("Authorization") String bearerToken,
      @RequestBody Clan clan) {
    // Verify the JWT token and extract the userId
    String jwtToken = bearerToken.substring(7);
    String userId = jwtTokenProvider
        .getUsernameFromToken(jwtToken)
        .getPayload()
        .getSubject();

    // Clan add logic
    Clan newClan = clan;
    newClan.setOwnerId(Integer.parseInt(userId));
    clanRepository.save(newClan);

    // UserClan association logic
    User newUser = userRepository.findById(Integer.parseInt(userId)).orElseThrow();
    UserClan newUserClan = new UserClan(newUser, newClan);
    userClanRepository.save(newUserClan);

    return ResponseEntity
        .ok()
        .body(new ApiResponse<>("Clan successfully created", null, new Date()));
  }

  @CrossOrigin
  @GetMapping("/getFromClanName")
  ResponseEntity<ApiResponse<?>> getFromClanName(@RequestParam String clanName) {
    Clan name = clanRepository.findByClanName(clanName);
    Integer clanId = name.getId();
    List<UserClan> userClan = userClanRepository.findByClanId(clanId);
    List<User> users = new ArrayList<User>();
    for (UserClan u : userClan) {
      User user = u.getUser();
      users.add(user);
    }

    return ResponseEntity.ok().body(new ApiResponse<>("Users from clan retrieved", users, new Date()));
  }

  @CrossOrigin
  @GetMapping("/getFromUserId")
  ResponseEntity<ApiResponse<?>> getFromUserId(@RequestParam String userId) {
    List<UserClan> userClans = userClanRepository.findByUserId(Integer.parseInt(userId));
    List<Clan> clans = new ArrayList<Clan>();
    for (UserClan c : userClans) {
      Clan clan = c.getClan();
      clans.add(clan);
    }

    return ResponseEntity.ok().body(new ApiResponse<>("Clans from user retrieved", clans, new Date()));
  };

  @PostMapping("/addUserToClan")
  ResponseEntity<ApiResponse<?>> addUserToClan(@RequestHeader("Authorization") String bearerToken,
      @RequestBody Map<String, String> request) {
    try {
      // Verify the JWT token and extract the userId
      String jwtToken = bearerToken.substring(7);
      String userId = jwtTokenProvider.getUsernameFromToken(jwtToken).getPayload().getSubject();

      // Add user logic
      User newUser = userRepository.findByUsername(request.get("username"));
      List<Clan> clanList = clanRepository.findByOwnerId(Integer.parseInt(userId));
      Clan clan = null;
      for (Clan c : clanList) {
        if (c.getClanName().equals(request.get("clanName"))) {
          clan = c;
        }
      }

      // Return exception: User already belongs to the clan
      Integer newUserId = newUser.getId();
      Integer clanId = clan.getId();
      if (userClanRepository.findByUserIdAndClanId(newUserId, clanId) != null) {
        throw new UsernameAlreadyExistsException("Member already in group");
      }

      UserClan newUserClan = new UserClan(newUser, clan);

      userClanRepository.save(newUserClan);

      // Clan clan = clanRepository.find
      return ResponseEntity.ok().body(new ApiResponse<>("User added to clan", null, new Date()));
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
      String jwtToken = bearerToken.substring(7);
      String userId = jwtTokenProvider.getUsernameFromToken(jwtToken).getPayload().getSubject();

      // Delete clan logic
      List<UserClan> userClanList = userClanRepository.findByUserId(Integer.parseInt(userId));
      Clan deletedClan = null;
      System.out.println("BEFORE THE FOR LOOP");
      for (UserClan uc : userClanList) {
        Clan c = uc.getClan();
        System.out.println("\n" + c.getOwnerId() + " " + userId);
        if (c.getOwnerId() == Integer.parseInt(userId)) {
          System.out.println("Are we ever getting here");
          deletedClan = c;
          break; // Requestee is the owner of the clan
        }
      }

      if (deletedClan == null) {
        // If requestee was not the clan owner
        throw new UserIsNotClanOwnerException();
      }

      clanRepository.delete(deletedClan);
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
      String jwtToken = bearerToken.substring(7);
      String userId = jwtTokenProvider.getUsernameFromToken(jwtToken).getPayload().getSubject();

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

  @PostMapping("/test")
  ResponseEntity<?> test(
      @RequestHeader("Authorization") String bearerToken,
      @RequestBody String test) {
    System.out.println("\n\n" + bearerToken + "\n\n" + test);
    return ResponseEntity.ok().body(new ApiResponse<>(test, bearerToken, new Date()));
  }
}
