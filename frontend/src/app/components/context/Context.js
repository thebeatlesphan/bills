import React, { createContext, useReducer } from "react";

// Define the initial state
const initialState = {
  userId: null,
  username: null,
  isAuthenticated: false,
  token: null,
  currentClan: null,
  clans: [],
};

// Create the context
const AuthContext = createContext(initialState);

// Create a reducer function to handle state changes
const authReducer = (state, action) => {
  switch (action.type) {
    case "LOGIN":
      return {
        ...state,
        userId: action.payload.userId,
        username: action.payload.username,
        isAuthenticated: true,
        token: action.payload.token,
      };
    case "LOGOUT":
      return {
        ...state,
        userId: null,
        username: null,
        isAuthenticated: false,
        token: null,
        currentClan: null,
        clans: null,
      };
    case "SELECTCLAN":
      return {
        ...state,
        currentClan: action.payload.clanName,
      };
    case "CLANLIST":
      return {
        ...state,
        clans: action.payload.clans,
      };
    default:
      return state;
  }
};

// Create a custom provider component
export const AuthProvider = ({ children }) => {
  const [state, dispatch] = useReducer(authReducer, initialState);

  const login = ({ userId, username, isAuthenticated, token }) => {
    dispatch({
      type: "LOGIN",
      payload: { userId, username, isAuthenticated, token },
    });
  };

  const logout = () => {
    dispatch({ type: "LOGOUT" });
  };

  const selectClan = (clanName) => {
    console.log(clanName);
    dispatch({ type: "SELECTCLAN", payload: { clanName } });
  };

  const clanList = (clans) => {
    dispatch({ type: "CLANLIST", payload: { clans } });
  };

  return (
    <AuthContext.Provider
      value={{ ...state, login, logout, selectClan, clanList }}
    >
      {children}
    </AuthContext.Provider>
  );
};

// Create a custom hook to access the context
export const useAuth = () => {
  return React.useContext(AuthContext);
};
