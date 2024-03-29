import React, { useEffect, useState } from "react";
import AuthForm from "./components/authform/AuthForm";
import Navbar from "./components/navbar/Navbar";
import { useAuth } from "./components/context/Context";
import styles from "./styles/Home.module.css";
import Form from "./components/form/Form";
import InputField from "./components/form/InputField";
import Button from "./components/button/Button";
import CurrentClan from "./components/currentclan/CurrentClan";
import Expense from "./components/expense/Expense";
import ExpenseOverview from "./components/expense/ExpenseOverview";
import ClanList from "./components/clanlist/ClanList";

const Home = () => {
  const {
    isAuthenticated,
    currentClan,
    expenses,
    clans,
    setCurrentClan,
    getMembers,
    getExpenses,
    addExpense,
  } = useAuth();

  const [expenseForm, setExpenseForm] = useState({
    expense: "",
    amount: "",
    expenseDate: "",
  });

  useEffect(() => {
    console.log("home rerendered");
  }, [clans, expenses]);

  const handleExpenseChange = (fieldName, value) => {
    setExpenseForm({
      ...expenseForm,
      [fieldName]: value,
    });
  };

  const handleAddExpense = async (e) => {
    e.preventDefault();

    const url = `${process.env.NEXT_PUBLIC_API}api/expense/add`;
    const token = sessionStorage.getItem("jwtToken");
    const data = { ...expenseForm, clanName: currentClan };

    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(data),
    });

    const reply = await response.json();
    if (!response.ok) {
      window.alert("Failed to add expense");
    } else {
      addExpense(reply.data.expense);
      setExpenseForm({ expense: "", amount: "", expenseDate: "" });
    }
  };

  const handleSelectCurrentClan = async (clanName) => {
    // Update context
    setCurrentClan(clanName);

    // Current members list
    const url = `${process.env.NEXT_PUBLIC_API}api/clan/getFromClanName?clanName=${clanName}`;
    const response = await fetch(url);

    const reply = await response.json();
    console.log(reply);
    if (!response.ok) {
      window.alert("Failed to get clan members.");
    } else {
      getMembers(reply.data);
    }

    // Current expenses list
    const _url = `${process.env.NEXT_PUBLIC_API}api/expense/getClanExpenses?clanName=${clanName}`;
    const token = sessionStorage.getItem("jwtToken");
    const _response = await fetch(_url, {
      method: "GET",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });

    if (!_response.ok) {
      window.alert("Failed to get clan expenses.");
    } else {
      const _reply = await _response.json();
      getExpenses(_reply.data);
      console.log(_reply.data);
    }
  };

  return (
    <div className={styles.home}>
      <div className={styles.background}></div>
      {isAuthenticated ? (
        <>
          <Navbar />
          <div className={styles.container}>
            <div className={styles.billsContainer}>
              <img
                className={styles.bills}
                src="icons/bill.png"
                alt="bills icon from Flaticon.com"
              />
            </div>
            {clans == null || clans.length == 0 ? (
              <div className={styles.noClan}>
                You do not belong to any clan. Please create a clan.
              </div>
            ) : (
              clans.map((clan) => (
                <ClanList
                  key={clan.clan.id}
                  {...clan}
                  isActive={clan.clan.clanName === currentClan}
                  onClick={() => handleSelectCurrentClan(clan.clan.clanName)}
                />
              ))
            )}

            {currentClan == null ? <></> : <CurrentClan />}

            {expenses == null ? <></> : <ExpenseOverview expenses={expenses} />}

            {currentClan == null ? (
              <></>
            ) : (
              <>
                <Form title="Add Expense" onSubmit={handleAddExpense}>
                  <InputField
                    type="text"
                    label="Expense"
                    value={expenseForm.expense}
                    onChange={(e) =>
                      handleExpenseChange("expense", e.target.value)
                    }
                  ></InputField>
                  <InputField
                    type="text"
                    label="Amount"
                    value={expenseForm.amount}
                    onChange={(e) =>
                      handleExpenseChange("amount", e.target.value)
                    }
                  ></InputField>
                  <InputField
                    type="date"
                    label="Date"
                    value={expenseForm.expenseDate}
                    onChange={(e) =>
                      handleExpenseChange("expenseDate", e.target.value)
                    }
                  ></InputField>

                  <Button
                    type="submit"
                    label="Add Expense"
                    disabled={
                      expenseForm.amount == "" ||
                      expenseForm.expense == "" ||
                      expenseForm.expenseDate == ""
                        ? true
                        : false
                    }
                  />
                </Form>
              </>
            )}

            {expenses == null ? (
              <></>
            ) : (
              expenses.map((exp) => (
                <Expense
                  key={exp.id}
                  id={exp.id}
                  name={exp.name}
                  amount={exp.amount}
                  expenseDate={exp.expenseDate}
                ></Expense>
              ))
            )}
          </div>
        </>
      ) : (
        <AuthForm />
      )}
    </div>
  );
};

export default Home;
