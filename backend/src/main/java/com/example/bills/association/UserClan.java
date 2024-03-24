package com.example.bills.association;

import com.example.bills.clan.Clan;
import com.example.bills.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserClan {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne()
  @JoinColumn(name = "clan_id")
  private Clan clan;

  public UserClan() {
  }

  public UserClan(User user, Clan clan) {
    this.user = user;
    this.clan = clan;
  }

  // Getters and setters
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Clan getClan() {
    return clan;
  }

  public void setClan(Clan clan) {
    this.clan = clan;
  }
}
