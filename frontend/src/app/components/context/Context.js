import React, { createContext, useReducer } from "react";

// Define the initial state
const initialState = {
  userId: null,
  username: null,
  isAuthenticated: false,
  token: null,
  currentClan: null,
  clans: null,
  expenses: null,
  members: null,
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
        expenses: null,
        members: null,
      };
    case "UPDATECURRENTCLAN":
      return {
        ...state,
        currentClan: action.payload.clanName,
      };
    case "CREATE_CLAN":
      return {
        ...state,
        clans: [...state.clans, action.payload.clan],
      };
    case "DELETE_CLAN":
      return {
        ...state,
        clans: state.clans.filter(
          (clan) => clan.clan.clanName != action.payload.clanName
        ),
      };
    case "CLANEXPENSES":
      return {
        ...state,
        expenses: action.payload.expenses,
      };
    case "GET_CLANS":
      return {
        ...state,
        clans: action.payload.clans,
      };
    case "MEMBERS":
      return {
        ...state,
        members: action.payload.members,
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

  const updateCurrentClan = (clanName) => {
    dispatch({ type: "UPDATECURRENTCLAN", payload: { clanName } });
  };

  const createClan = (clan) => {
    dispatch({ type: "CREATE_CLAN", payload: { clan } });
  };

  const deleteClan = (clanName) => {
    dispatch({ type: "DELETE_CLAN", payload: { clanName } });
  };

  const getClans = (clans) => {
    dispatch({ type: "GET_CLANS", payload: { clans } });
  };

  const clanExpenses = (expenses) => {
    dispatch({ type: "CLANEXPENSES", payload: { expenses } });
  };

  const membersList = (members) => {
    dispatch({ type: "MEMBERS", payload: { members } });
  };

  return (
    <AuthContext.Provider
      value={{
        ...state,
        login,
        logout,
        updateCurrentClan,
        createClan,
        deleteClan,
        getClans,
        clanExpenses,
        membersList,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

// Create a custom hook to access the context
export const useAuth = () => {
  return React.useContext(AuthContext);
};
