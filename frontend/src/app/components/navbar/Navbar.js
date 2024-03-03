import { useState } from "react";
import { useAuth } from "../context/Context";
import styles from "./Navbar.module.css";

function Navbar() {
  const { username, logout } = useAuth();
  const [menu, setMenu] = useState(false);

  const handleMenu = () => {
    setMenu((prev) => !prev);
  };

  return (
    <nav className={styles.navbar}>
      <ul className={styles.unordered}>
        <li>Group</li>
        <li>Expense</li>
        <li className={styles.username} onClick={handleMenu}>
          {username}
        </li>
      </ul>
      <p
        className={`${styles.signOut} ${menu && styles.visible}`}
        onClick={logout}
      >
        sign out
      </p>
    </nav>
  );
}

export default Navbar;
