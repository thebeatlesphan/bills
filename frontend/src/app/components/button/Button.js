import styles from "./Button.module.css";

function Button({ onClick, label, ...restProps }) {
  return (
    <button
      onClick={onClick}
      className={`${styles.button} ${restProps.className}`}
      {...restProps}
    >
      {label}
    </button>
  );
}

export default Button;
