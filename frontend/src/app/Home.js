import React from "react";
import AuthForm from "./components/authform/AuthForm";
import Navbar from "./components/navbar/Navbar";
import { useAuth } from "./components/context/Context";
import styles from "./styles/Home.module.css";

const Home = () => {
  const { isAuthenticated } = useAuth();
  console.log(`is authenticated:`, isAuthenticated);

  return (
    <div className={styles.home}>
      {isAuthenticated ? (
        <>
          {" "}
          <Navbar /> <h1>WELCOME</h1>
        </>
      ) : (
        <AuthForm />
      )}
    </div>
  );
};

export default Home;
