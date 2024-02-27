package com.example.bills.clan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/group")
public class ClanController {
    @Autowired
    private ClanRepository clanRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewGroup(@RequestParam String clanName) {
        Clan n = new Clan();
        n.setGroupName(clanName);
        clanRepository.save(n);
        return "Saved";
    }
}
