import { useState } from "react";
import { useAuth } from "../context/Context";
import Button from "../button/Button";
import styles from "./CurrentClan.module.css";

const CurrentClan = (...props) => {
  const { userId, currentClan, clans, selectClan, clanList } = useAuth();
  const [isOpen, setIsOpen] = useState(false);

  const transClass = isOpen ? styles.clans : styles.hidden;

  const handleClansList = async () => {
    // toggle clan list dropdown
    setIsOpen((prev) => !prev);

    // We are using JPA repository rest resource param query here
    const url = `${process.env.NEXT_PUBLIC_API}api/clan/getFromUserId?userId=${userId}`;
    const response = await fetch(url);

    const reply = await response.json();
    clanList(reply.data);
  };

  const handleClanUsers = async (clanName) => {
    // Update context first
    selectClan(clanName);
    setIsOpen((prev) => !prev);

    const url = `${process.env.NEXT_PUBLIC_API}api/clan/getFromClanName?clanName=${clanName}`;
    const response = await fetch(url);
    const reply = await response.json();
    console.log(reply);
  };

  const handleAddUser = async () => {
    const url = `${process.env.NEXT_PUBLIC_API}api/clan/add`;
  }

  return (
    <div className={styles.dropdown}>
      <Button
        label={currentClan == null ? "Select Clan" : "Change Clan"}
        onClick={handleClansList}
      />
      {clans == null ? (
        <></>
      ) : (
        <div>
          {clans.map((clan) => (
            <li
              key={clan.id}
              className={transClass}
              onClick={() => handleClanUsers(clan.clanName)}
            >
              {clan.clanName}
            </li>
          ))}
        </div>
      )}
      {currentClan == null ? (
        ""
      ) : (
        <div>
          <div className={styles.current}>Clan {currentClan}</div>
          <div className={styles.buttons}>
            <Button label="Add Member" />
            <Button label="Remove Member" />
            <Button label="Delete Clan" />
          </div>
          <div>Members</div>
        </div>
      )}
    </div>
  );
};

export default CurrentClan;
