package com.example.bills.clan;

import java.util.List;

import com.example.bills.association.UserClan;
import com.example.bills.expense.Expense;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Clan {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(nullable = false)
  private String clanName;

  @Column(nullable = false)
  private Integer ownerId;

  @OneToMany(mappedBy = "clan", cascade = CascadeType.ALL)
  private List<UserClan> userClans;

  @OneToMany(mappedBy = "clan", cascade = CascadeType.ALL)
  private List<Expense> expenses;
  
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getClanName() {
    return clanName;
  }

  public void setClanName(String clanName) {
    this.clanName = clanName;
  }

  public Integer getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Integer ownerId) {
    this.ownerId = ownerId;
  }
}
