"use client";

import React from "react";
import AuthForm from "./components/authform/AuthForm";

const HomePage = () => {
  const handleLogin = (credentials) => {
    console.log("credentials: " + JSON.stringify({ credentials }));
  };

  return (
    <div>
      <AuthForm />
    </div>
  );
};

export default HomePage;
