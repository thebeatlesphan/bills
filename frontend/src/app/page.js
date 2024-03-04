"use client";

import React from "react";
import { AuthProvider, useAuth } from "./components/context/Context";
import Home from "./Home";
import './styles/globals.css';

const HomePage = () => {
  return (
    <AuthProvider>
      <Home />
    </AuthProvider>
  );
};

export default HomePage;
