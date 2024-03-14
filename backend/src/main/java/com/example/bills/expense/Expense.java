package com.example.bills.expense;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.bills.clan.Clan;

@Entity
public class Expense {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String name;
  private BigDecimal amount;
  private LocalDate expenseDate;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "clan_id")
  private Clan clan;

  public Expense() {
  }

  public Expense(String name, BigDecimal amount, LocalDate expenseDate) {
    this.name = name;
    this.amount = amount;
    this.expenseDate = expenseDate;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public LocalDate getExpenseDate() {
    return expenseDate;
  }

  public void setExpenseDate(LocalDate expenseDate) {
    this.expenseDate = expenseDate;
  }

  public Clan getClan() {
    return clan;
  }

  public void setClan(Clan clan) {
    this.clan = clan;
  }

  public String toString() {
    return name + expenseDate + amount;
  }
}
