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
      <div className={styles.unordered}>
        <div className={styles.username} onClick={handleMenu}>
          Welcome {username}
        </div>
      </div>
      <div className={`${styles.signOut} ${hidden}`} onClick={logout}>
        Sign Out
      </div>
    </nav>
  );
}

export default Navbar;
