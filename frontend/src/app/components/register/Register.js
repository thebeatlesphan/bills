import { useState } from "react";
import Button from "../button/Button";
import styles from "./Register.module.css";

const Register = () => {
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();

  const handleRegister = async (e) => {
    e.preventDefault();

    try {
      // Make a POST request to the backend registration endpoint
      const response = await fetch("localhost:8080");
    } catch (error) {
      console.error("Error during registration: ", error);
    }
  };

  return (
    <form className={styles.form} onSubmit={handleRegister}>
      <p className={styles.p}>Registration Form</p>
      <input
        type="text"
        placeholder="username"
        onChange={(e) => setUsername(e.target.value)}
      ></input>
      <input
        type="password"
        placeholder="password"
        onChange={(e) => setPassword(e.target.value)}
      ></input>
      <Button type="submit" label="REGISTER" />
    </form>
  );
};

export default Register;
