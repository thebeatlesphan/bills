import styles from "./ClanList.module.css";

const ClanList = ({ isActive, ...props }) => {
  let total = "0.00";
  if (props.monthlyTotal != null) {
    total = props.monthlyTotal;
  }

  return (
    <div
      className={`${styles.container} ${isActive ? styles.active : ""}`}
      onClick={props.onClick}
    >
      <div className={styles.iconAndClanName}>
        <img
          className={styles.icon}
          src="/icons/koala.png"
          alt="koala icon from Flaticon.com"
        />
        <span>{props.clan.clanName}</span>
      </div>
      <div className={styles.total}>
        ${total.toFixed(2)}
        <span className={styles.text}>Monthly Total</span>
      </div>
    </div>
  );
};

export default ClanList;
