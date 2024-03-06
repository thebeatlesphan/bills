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
  const { isAuthenticated } = useAuth();
  const [clanForm, setClanForm] = useState("");
  const [expenseForm, setExpenseForm] = useState({
    expense: "",
    amount: "",
    date: "",
  });

  const handleClanChange = (e) => {
    setClanForm(e.target.value);
  };

  const handleClanSubmit = async (e) => {
    e.preventDefault();

    const url = "http://10.0.0.239:8080/api/clan/add";
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

  const handleExpenseSubmit = () => {
    console.log("SUBMIT");
    console.log(expenseForm);
  };

  const test = async () => {
    const url = "http://10.0.0.239:8080/api/clan/test";
    const token = sessionStorage.getItem("jwtToken");
    const data = {
      test: "test",
    };

    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-type": "application/json",
        Authorization: `${token}`,
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
            <Form title="Expense" onSubmit={handleExpenseSubmit}>
              <InputField
                type="text"
                label="Expense"
                onChange={(e) => handleExpenseChange("expense", e.target.value)}
              ></InputField>
              <InputField
                type="text"
                label="Amount"
                onChange={(e) => handleExpenseChange("amount", e.target.value)}
              ></InputField>
              <InputField
                type="text"
                label="Date"
                onChange={(e) => handleExpenseChange("date", e.target.value)}
                placeholder="YYYY-MM-DD"
              ></InputField>
              <Button
                type="submit"
                label="Add Expense"
                disabled={
                  expenseForm.amount == "" ||
                  expenseForm.expense == "" ||
                  expenseForm.date == ""
                    ? true
                    : false
                }
              />
            </Form>
          </div>
        </>
      ) : (
        <AuthForm />
      )}
    </div>
  );
};

export default Home;
