package com.example.bills.clan;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bills.response.ApiResponse;

@RestController
@RequestMapping("/api/clan")
public class ClanController {
    private final ClanRepository clanRepository;

    @Autowired
    ClanController(ClanRepository clanRepository) {
        this.clanRepository = clanRepository;
    }

    @PostMapping("/add")
    ResponseEntity<ApiResponse<Clan>> registerGroup(@RequestBody Clan clan) {
        // Access owner from SecurityContext
        final Authentication principal = SecurityContextHolder.getContext().getAuthentication();

        Clan n = clan;
        n.setOwner(principal.getName());
        System.out.println(principal.getDetails() + "\n\n");
        System.out.println(principal.getName() + "\n\n");
        clanRepository.save(n);

        return ResponseEntity.ok().body(new ApiResponse<>("Clan successfully created", null, new Date()));

    }
}
