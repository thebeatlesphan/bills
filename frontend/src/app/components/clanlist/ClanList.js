import styles from "./ClanList.module.css";
// clan.clan clan.monthlyTotal

const ClanList = (props) => {
  let total = "0.00";
  if (props.monthlyTotal != null) {
    total = props.monthlyTotal;
  }

  return (
    <div className={styles.container} onClick={props.onClick}>
      <div className={styles.iconAndClanName}>
        <img
          className={styles.icon}
          src="/icons/koala.png"
          alt="koala icon from Flaticon.com"
        />
        <span>{props.clan.clanName}</span>
      </div>
      <div className={styles.total}>
        $ {total}
        <span className={styles.text}>Monthly Total</span>
      </div>
    </div>
  );
};

export default ClanList;
