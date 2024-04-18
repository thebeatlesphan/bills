import { useAuth } from "../context/Context";
import styles from "./CurrentClan.module.css";

const CurrentClan = () => {
  const { currentClan, members } = useAuth();

  return (
    <div className={styles.container}>
      <div className={styles.current}>{currentClan.clanName}</div>
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
