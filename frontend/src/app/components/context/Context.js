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
    case "SET_CURRENT_CLAN":
      return {
        ...state,
        currentClan: action.payload.clanName,
      };
    case "GET_CLANS":
      return {
        ...state,
        clans: action.payload.clans,
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
    case "GET_EXPENSES":
      return {
        ...state,
        expenses: action.payload.expenses,
      };
    case "ADD_EXPENSE":
      return {
        ...state,
        expenses: [...state.expenses, action.payload.expense],
      };
    case "GET_MEMBERS":
      return {
        ...state,
        members: action.payload.members,
      };
    case "ADD_MEMBER":
      return {
        ...state,
        members: [...state.members, action.payload.member],
      };
    case "REMOVE_MEMBER":
      return {
        ...state,
        members: state.members.filter(
          (member) => member.id != action.payload.userId
        ),
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

  const setCurrentClan = (clanName) => {
    dispatch({ type: "SET_CURRENT_CLAN", payload: { clanName } });
  };

  const getClans = (clans) => {
    dispatch({ type: "GET_CLANS", payload: { clans } });
  };

  const createClan = (clan) => {
    dispatch({ type: "CREATE_CLAN", payload: { clan } });
  };

  const deleteClan = (clanName) => {
    dispatch({ type: "DELETE_CLAN", payload: { clanName } });
  };

  const getExpenses = (expenses) => {
    dispatch({ type: "GET_EXPENSES", payload: { expenses } });
  };

  const addExpense = (expense) => {
    dispatch({ type: "ADD_EXPENSE", payload: { expense } });
  };

  const getMembers = (members) => {
    dispatch({ type: "GET_MEMBERS", payload: { members } });
  };

  const addMember = (member) => {
    dispatch({ type: "ADD_MEMBER", payload: { member } });
  };

  const removeMember = (userId) => {
    dispatch({ type: "REMOVE_MEMBER", payload: { userId } });
  };
  return (
    <AuthContext.Provider
      value={{
        ...state,
        login,
        logout,
        setCurrentClan,
        createClan,
        deleteClan,
        getClans,
        getExpenses,
        addExpense,
        getMembers,
        addMember,
        removeMember,
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
