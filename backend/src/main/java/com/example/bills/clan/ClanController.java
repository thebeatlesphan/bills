package com.example.bills.clan;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bills.jwt.JwtTokenProvider;
import com.example.bills.response.ApiResponse;

@RestController
@RequestMapping("/api/clan")
public class ClanController {
    private final ClanRepository clanRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    ClanController(ClanRepository clanRepository, JwtTokenProvider jwtTokenProvider) {
        this.clanRepository = clanRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/add")
    ResponseEntity<ApiResponse<Clan>> registerGroup(@RequestHeader("Authorization") String bearerToken,
            @RequestBody Clan clan) {

        // Verify the JWT token and extract the userId
        String jwtToken = bearerToken.substring(7);
        String userId = jwtTokenProvider.getUsernameFromToken(jwtToken).getPayload().getSubject();

        // Clan add logic
        Clan newClan = clan;
        newClan.setOwnerId(Integer.parseInt(userId));
        clanRepository.save(newClan);

        return ResponseEntity.ok().body(new ApiResponse<>("Clan successfully created", null, new Date()));

    }

    @PostMapping("/test")
    ResponseEntity<?> test(@RequestHeader("Authorization") String bearerToken, @RequestBody String test) {
        System.out.println("\n\n" + bearerToken + "\n\n" + test);
        return ResponseEntity.ok().body(new ApiResponse<>(test, bearerToken, new Date()));
    }
}
