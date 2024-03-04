import React, { useState } from "react";
import AuthForm from "./components/authform/AuthForm";
import Navbar from "./components/navbar/Navbar";
import { useAuth } from "./components/context/Context";
import styles from "./styles/Home.module.css";
import Form from "./components/form/Form";
import InputField from "./components/form/InputField";
import Button from "./components/button/Button";

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

  const handleClanSubmit = () => {
    console.log("SUBMIT!");
    console.log(clanForm);
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

  return (
    <div className={styles.home}>
      {isAuthenticated ? (
        <>
          <Navbar />
          <h1>WELCOME</h1>
          <Form title="Clan" onSubmit={handleClanSubmit}>
            <InputField
              type="text"
              label="Clan Name"
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
        </>
      ) : (
        <AuthForm />
      )}
    </div>
  );
};

export default Home;
