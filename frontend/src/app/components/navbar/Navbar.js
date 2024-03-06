import { useState } from "react";
import { useAuth } from "../context/Context";
import styles from "./Navbar.module.css";

function Navbar() {
  const { username, logout } = useAuth();
  const [menu, setMenu] = useState(false);

  const handleMenu = () => {
    setMenu((prev) => !prev);
  };

  const hidden = menu ? "" : styles.hidden;

  return (
    <nav className={styles.navbar}>
      <ul className={styles.unordered}>
        <li>Group</li>
        <li>Expense</li>
        <li className={styles.username} onClick={handleMenu}>
          {username}
        </li>
      </ul>
      <div className={`${styles.signOut} ${hidden}`} onClick={logout}>
        Sign Out
      </div>
    </nav>
  );
}

export default Navbar;
