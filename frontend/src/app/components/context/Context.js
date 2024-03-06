import React, { createContext, useReducer } from "react";

// Define the initial state
const initialState = {
  username: null,
  isAuthenticated: false,
  token: null,
  currentClan: null,
  clans: null,
};

// Create the context
const AuthContext = createContext(initialState);

// Create a reducer function to handle state changes
const authReducer = (state, action) => {
  switch (action.type) {
    case "LOGIN":
      return {
        ...state,
        username: action.payload.username,
        isAuthenticated: true,
        token: action.payload.token,
        clans: action.payload.clans,
      };
    case "LOGOUT":
      return {
        ...state,
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
    default:
      return state;
  }
};

// Create a custom provider component
export const AuthProvider = ({ children }) => {
  const [state, dispatch] = useReducer(authReducer, initialState);

  const login = ({ username, isAuthenticated, token, clans }) => {
    dispatch({
      type: "LOGIN",
      payload: { username, isAuthenticated, token, clans },
    });
  };

  const logout = () => {
    dispatch({ type: "LOGOUT" });
  };

  const selectClan = (clanName) => {
    console.log(clanName);
    dispatch({ type: "SELECTCLAN", payload: { clanName } });
  };

  return (
    <AuthContext.Provider value={{ ...state, login, logout, selectClan }}>
      {children}
    </AuthContext.Provider>
  );
};

// Create a custom hook to access the context
export const useAuth = () => {
  return React.useContext(AuthContext);
};
