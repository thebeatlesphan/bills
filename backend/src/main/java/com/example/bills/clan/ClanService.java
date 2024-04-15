package com.example.bills.clan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bills.association.UserClan;
import com.example.bills.association.UserClanRepository;
import com.example.bills.expense.ExpenseRepository;
import com.example.bills.user.User;
import com.example.bills.user.UserDTO;
import com.example.bills.user.UserRepository;
import com.example.bills.user.UserService;

import jakarta.transaction.Transactional;

@Service
public class ClanService {
    private final ClanRepository clanRepository;
    private final ExpenseRepository expenseRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserClanRepository userClanRepository;

    @Autowired
    public ClanService(
            ClanRepository clanRepository,
            ExpenseRepository expenseRepository,
            UserService userService,
            UserRepository userRepository,
            UserClanRepository userClanRepository) {
        this.clanRepository = clanRepository;
        this.expenseRepository = expenseRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.userClanRepository = userClanRepository;
    }

    public List<UserClan> getAllClansFromUserId(Integer userId) {
        return userClanRepository.findByUserId(userId);
    }

    // Clan names are not unique therefore we first retrieve user's clan list
    public Clan getClanByName(Integer userId, String clanName) {
        List<UserClan> clans = getAllClansFromUserId(userId);
        Clan clan = null;
        for (UserClan c : clans) {
            if (c.getClan().getClanName().equals(clanName))
                clan = c.getClan();
        }
        return clan;
    }

    @Transactional
    public Clan addClan(Integer ownerId, Clan clan) {
        Clan newClan = clan;
        newClan.setOwnerId(ownerId);
        clanRepository.save(newClan);

        // TODO: JPA and Hibernate will create association tables automatically.
        // TODO: I've chosen to do it manually errorneously
        User owner = userRepository.findById(ownerId).orElseThrow();
        UserClan userClan = new UserClan(owner, newClan);
        userClanRepository.save(userClan);
        return clan;
    }

    public Clan getClanById(Integer clanId) {
        return clanRepository.findById(clanId).orElseThrow();
    }

    public List<UserDTO> getUsersFromClanId(Integer clanId) {
        Clan clan = getClanById(clanId);
        List<UserDTO> users = new ArrayList<>();
        List<UserClan> userClans = userClanRepository.findByClanId(clan.getId());
        for (UserClan userClan : userClans) {
            User user = userClan.getUser();
            UserDTO userDTO = new UserDTO(user.getId(), user.getUsername());
            users.add(userDTO);
        }
        return users;
    }

    public Map<String, Object> getClanTotal(Clan clan) {
        Map<String, Object> _clan = new HashMap<>();

        // Query paramters
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();

        Float monthlyTotal = expenseRepository.getTotalExpenseForMonthAndClan(month, year, clan);
        _clan.put("clan", clan);
        _clan.put("monthlyTotal", monthlyTotal);
        return _clan;
    }

    public List<Map<String, Object>> getClanTotalsFromUserClan(List<UserClan> userClans) {
        List<Map<String, Object>> clanTotals = new ArrayList<>();
        for (UserClan userClan : userClans) {
            Clan clan = userClan.getClan();
            clanTotals.add(getClanTotal(clan));
        }
        return clanTotals;
    }

    public void addUserClan(User user, Clan clan) {
        UserClan userClan = new UserClan(user, clan);
        userClanRepository.save(userClan);
    }

    public boolean userClanExists(User user, Clan clan) {
        Integer userId = user.getId();
        Integer clanId = clan.getId();
        return userClanRepository.findByUserIdAndClanId(userId, clanId) != null;
    }
}