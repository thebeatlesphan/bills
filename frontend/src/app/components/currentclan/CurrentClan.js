import { useState } from "react";
import { useAuth } from "../context/Context";
import styles from "./CurrentClan.module.css";

const CurrentClan = () => {
  const {
    userId,
    currentClan,
    selectClan,
    clanList,
    clanExpenses,
    members,
    membersList,
  } = useAuth();

  const handleClanUsers = async (clanName) => {
    // Update context first
    selectClan(clanName);
    setIsOpen((prev) => !prev);

    // Current members list
    const url = `${process.env.NEXT_PUBLIC_API}api/clan/getFromClanName?clanName=${clanName}`;
    const response = await fetch(url);

    if (!response.ok) {
      window.alert("Failed to get clan members.");
    } else {
      const reply = await response.json();
      membersList(reply.data);
    }

    // Current expenses list
    const _url = `${process.env.NEXT_PUBLIC_API}api/expense/getClanExpenses?clanName=${clanName}`;
    const token = sessionStorage.getItem("jwtToken");
    const _response = await fetch(_url, {
      method: "GET",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });

    if (!_response.ok) {
      window.alert("Failed to get clan expenses.");
    } else {
      const _reply = await _response.json();
      clanExpenses(_reply.data);
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.current}>{currentClan}</div>
      <div className={styles.clanMembersTitle}>Clan Members</div>
      <div className={styles.clanMembers}>
        {members == null ? (
          <></>
        ) : (
          members.map((member) => (
            <div key={member.username}>{member.username}</div>
          ))
        )}
      </div>
    </div>
  );
};

export default CurrentClan;
