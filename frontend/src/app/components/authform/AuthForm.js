import React, { useEffect, useState } from "react";
import Login from "../login/Login";
import Register from "../register/Register";
import Button from "../button/Button";
import styles from "./AuthForm.module.css";

const AuthForm = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [registrationStatus, setRegistrationStatus] = useState(null);

  const toggleForm = () => {
    setIsLogin(!isLogin);
  };

  const handleRegistrationStatus = (status) => {
    setRegistrationStatus(status);
  };

  useEffect(() => {
    // If registration is successful, automatically switch to login form
    if (registrationStatus == "success") {
      setIsLogin(true);
    }
  }, [registrationStatus]);

  return (
    <div className={styles.authform}>
      {isLogin ? (
        <Login />
      ) : (
        <Register onRegistrationStatus={handleRegistrationStatus} />
      )}
      <p className={styles.p}>
        {isLogin ? "Not a member?" : "Already a member?"}
        <Button
          className={styles.button}
          label={isLogin ? "Register" : "Login"}
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
