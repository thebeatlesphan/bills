package com.example.bills.clan;

import com.example.bills.association.UserClan;
import com.example.bills.association.UserClanRepository;
import com.example.bills.jwt.JwtTokenProvider;
import com.example.bills.response.ApiResponse;
import com.example.bills.user.User;
import com.example.bills.user.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    };

    return ResponseEntity.ok().body(new ApiResponse<>("Clans from user retrieved", clans, new Date()));
  };

  @PostMapping("/addUserToClan")
  String addUserToClan(@RequestHeader("Authorization") String bearerToken, @RequestBody String username, String clanName) {
    return username + " " + clanName;
  }

  @PostMapping("/test")
  ResponseEntity<?> test(
      @RequestHeader("Authorization") String bearerToken,
      @RequestBody String test) {
    System.out.println("\n\n" + bearerToken + "\n\n" + test);
    return ResponseEntity.ok().body(new ApiResponse<>(test, bearerToken, new Date()));
  }
}
