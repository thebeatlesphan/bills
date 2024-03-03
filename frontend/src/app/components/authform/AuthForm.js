import React, { useEffect, useState } from "react";
import Login from "../login/Login";
import Register from "../register/Register";
import Button from "../button/Button";
import styles from "./AuthForm.module.css";

const AuthForm = () => {
  const [isLoginForm, setIsLoginForm] = useState(true);
  const [registrationStatus, setRegistrationStatus] = useState(null);

  const toggleForm = () => {
    setIsLoginForm(!isLoginForm);
  };

  const handleRegistrationStatus = (status) => {
    setRegistrationStatus(status);
  };

  useEffect(() => {
    // If registration is successful, automatically switch to login form
    if (registrationStatus == "success") {
      setIsLoginForm(true);
    }
  }, [registrationStatus]);

  return (
    <div className={styles.authform}>
      {isLoginForm ? (
        <Login />
      ) : (
        <Register onRegistrationStatus={handleRegistrationStatus} />
      )}
      <p className={styles.p}>
        {isLoginForm ? "Not a member?" : "Already a member?"}
        <Button
          className={styles.button}
          label={isLoginForm ? "Register" : "Login"}
          onClick={toggleForm}
        />
      </p>
      {registrationStatus && (
        <p className={styles.registrationStatus}>
          {registrationStatus === "success"
            ? "Registration successful! Please login."
            : "Registration failed. Please try again."}
        </p>
      )}
    </div>
  );
};

export default AuthForm;
