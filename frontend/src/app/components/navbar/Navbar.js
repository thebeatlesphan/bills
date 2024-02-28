import styles from "./Navbar.module.css";

function Navbar() {
  return (
    <nav className={styles.navbar}>
      <ul className={styles.unordered}>
        <li>one</li>
        <li>two</li>
        <li>three</li>
        <li>four</li>
      </ul>
    </nav>
  );
}

export default Navbar;
