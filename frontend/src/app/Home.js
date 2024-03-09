import React, { useState } from "react";
import AuthForm from "./components/authform/AuthForm";
import Navbar from "./components/navbar/Navbar";
import { useAuth } from "./components/context/Context";
import styles from "./styles/Home.module.css";
import Form from "./components/form/Form";
import InputField from "./components/form/InputField";
import Button from "./components/button/Button";
import CurrentClan from "./components/currentclan/CurrentClan";

const Home = () => {
  const { isAuthenticated, currentClan } = useAuth();
  const [clanForm, setClanForm] = useState("");
  const [expenseForm, setExpenseForm] = useState({
    expense: "",
    amount: "",
    expenseDate: "",
  });

  const handleClanChange = (e) => {
    setClanForm(e.target.value);
  };

  const handleClanSubmit = async (e) => {
    e.preventDefault();

    const url = `${process.env.NEXT_PUBLIC_API}api/clan/add`;
    const token = sessionStorage.getItem("jwtToken");
    const data = {
      clanName: clanForm,
    };

    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(data),
    });

    if (!response.ok) {
      window.alert("Failed to add clan. Please try again.");
      setClanForm("");
    } else {
      setClanForm("");
    }
  };

  const handleExpenseChange = (fieldName, value) => {
    setExpenseForm({
      ...expenseForm,
      [fieldName]: value,
    });
  };

  const handleExpenseSubmit = async (e) => {
    e.preventDefault();

    const url = `${process.env.NEXT_PUBLIC_API}api/expense/add`;
    const token = sessionStorage.getItem("jwtToken");
    const data = { ...expenseForm, clanName: currentClan };

    console.log(data);

    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(data),
    });

    const reply = await response.json();
    console.log(reply);
  };

  return (
    <div className={styles.home}>
      {isAuthenticated ? (
        <>
          <Navbar />
          <div className={styles.container}>
            <h1 className={styles.h1}> bills</h1>
            <CurrentClan />
            <Form title="Clan" onSubmit={handleClanSubmit}>
              <InputField
                type="text"
                label="Clan Name"
                value={clanForm}
                onChange={handleClanChange}
              />
              <Button
                type="submit"
                label="Add Clan"
                disabled={clanForm == "" ? true : false}
              />
            </Form>
            {currentClan == null ? (
              <></>
            ) : (
              <Form title="Expense" onSubmit={handleExpenseSubmit}>
                <InputField
                  type="text"
                  label="Expense"
                  onChange={(e) =>
                    handleExpenseChange("expense", e.target.value)
                  }
                ></InputField>
                <InputField
                  type="text"
                  label="Amount"
                  onChange={(e) =>
                    handleExpenseChange("amount", e.target.value)
                  }
                ></InputField>
                <InputField
                  type="date"
                  label="Date"
                  onChange={(e) => handleExpenseChange("expenseDate", e.target.value)}
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
