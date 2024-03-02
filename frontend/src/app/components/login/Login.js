import { useState } from "react";

import Button from "../button/Button";
import styles from "./Login.module.css";

const Login = ({ onLogin }) => {
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();

  const handleSubmit = (e) => {
    e.preventDefault();
    onLogin({ username, password });
  };

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
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
      <Button type="submit" label="LOGIN" />
    </form>
  );
};

export default Login;
