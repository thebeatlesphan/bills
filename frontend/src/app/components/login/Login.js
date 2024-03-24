import { useState } from "react";
import { useAuth } from "../context/Context";

import Button from "../button/Button";
import styles from "./Login.module.css";

const Login = () => {
  const { login, updateClanList, clans } = useAuth();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const onLogin = (userData) => {
    login(userData);
  };

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      // Make a POST request to the backend login endpoint
      const url = `${process.env.NEXT_PUBLIC_API}login`;
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

      const reply = await response.json();
      if (!response.ok) {
        window.alert(reply.message);
      } else {
        // Save JWT to Session Storage after authenticating
        const token = reply.token;
        console.log(reply);
        sessionStorage.setItem("jwtToken", token);

        // Update context
        onLogin(reply);

        // Fetch user's clan list
        handleClanMonthlyList(reply.userId);
      }
    } catch (error) {
      console.error("Error during login: ", error);
    }
  };

  const handleClanMonthlyList = async (userId) => {
    const url = `${process.env.NEXT_PUBLIC_API}api/clan/getFromUserId?userId=${userId}`;
    const response = await fetch(url);

    const reply = await response.json();
    console.log(reply);
    if (!response.ok) {
      window.alert(reply.message);
    } else {
      updateClanList(reply.data);
    }
  };

  return (
    <form className={styles.form} onSubmit={handleLogin}>
      <p className={styles.p}>Sign In</p>
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
