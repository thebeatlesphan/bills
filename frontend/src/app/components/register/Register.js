import { useState } from "react";
import Button from "../button/Button";
import styles from "./Register.module.css";

const Register = ({ onRegistrationStatus }) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleRegister = async (e) => {
    e.preventDefault();

    try {
      // Make a POST request to the backend registration endpoint
      const url = "http://10.0.0.239:8080/api/auth/register";
      const data = {
        username: username,
        password: password,
      };

      const response = await fetch(url, {
        method: "POST",
        headers: {
          "Content-type": "application/json",
        },
        body: JSON.stringify(data),
      });

      if (!response.ok) {
        // If the response is not OK, handle the error
        onRegistrationStatus("failure");
      } else {
        // Registration successful
        onRegistrationStatus("success");
      }
    } catch (error) {
      onRegistrationStatus("failure");
    }
  };

  return (
    <form className={styles.form} onSubmit={handleRegister} name="register">
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
      <Button
        type="submit"
        disabled={username == "" || password == "" ? true : false}
        label="REGISTER"
      />
    </form>
  );
};

export default Register;
