import { useState } from "react";
import { useAuth } from "../context/Context";
import Button from "../button/Button";
import styles from "./CurrentClan.module.css";

const CurrentClan = (...props) => {
  const { currentClan, clans, selectClan } = useAuth();
  const [isOpen, setIsOpen] = useState(false);

  const toggle = () => {
    setIsOpen((prev) => !prev);
  };

  const transClass = isOpen ? styles.clans : styles.hidden;

  return (
    <div className={styles.dropdown}>
      <Button label="Change Clan" onClick={toggle} />
      <div>
        {clans.map((clan) => (
          <li
            key={clan.id}
            className={transClass}
            onClick={() => selectClan(clan.clanName)}
          >
            {clan.clanName}
          </li>
        ))}
      </div>
      <div className={styles.current}>Members of Clan: {currentClan}</div>
    </div>
  );
};

export default CurrentClan;
