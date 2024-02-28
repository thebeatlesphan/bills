import React, { useState } from "react";
import Login from "../login/Login";
import Register from "../register/Register";
import Button from "../button/Button";
import styles from "./AuthForm.module.css";

const AuthForm = () => {
  const [isLogin, setIsLogin] = useState(true);

  const toggleForm = () => {
    setIsLogin(!isLogin);
  };

  return (
    <div className={styles.authform}>
      {isLogin ? <Login /> : <Register />}
      <p className={styles.p}>
        {isLogin ? "Not a member?" : "Already a member?"}
        <Button
          className={styles.button}
          label={isLogin ? "Register" : "Login"}
          onClick={toggleForm}
        />
      </p>
    </div>
  );
};

export default AuthForm;
