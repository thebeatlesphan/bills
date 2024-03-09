package com.example.bills.association;

import com.example.bills.clan.Clan;
import com.example.bills.expense.Expense;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ExpenseClan {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "clan_id")
  private Clan clan;

  @ManyToOne
  @JoinColumn(name = "expense_id")
  private Expense expense;

  public ExpenseClan() {
  }

  public ExpenseClan(Clan clan, Expense expense) {
    this.clan = clan;
    this.expense = expense;
  }

  public Clan getClan() {
    return clan;
  }

  public void setClan(Clan clan) {
    this.clan = clan;
  }

  public Expense getExpense() {
    return expense;
  }

  public void setExpense(Expense expense) {
    this.expense = expense;
  }
}
