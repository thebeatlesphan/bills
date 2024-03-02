import { useState } from "react";

import Button from "../button/Button";
import styles from "./Login.module.css";

const Login = ({ onLogin }) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      // Make a POST request to the backend login endpoint
      const url = "http://10.0.0.239:8080/login";
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
        const error = await response.json();
        console.log(error.message)
      } else {
        const reply = await response.json();
        console.log(reply.data);
      }
    } catch (error) {
      console.error("Error during login: ", error);
    }
  };

  return (
    <form className={styles.form} onSubmit={handleLogin}>
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
        label="LOGIN"
      />
    </form>
  );
};

export default Login;
