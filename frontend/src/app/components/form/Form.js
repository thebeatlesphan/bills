import { useState } from "react";
import styles from "./Form.module.css";

const Form = ({ onSubmit, title, children, isActive, ...props }) => {
  const [expand, setExpand] = useState(true);

  const handleExpand = () => {
    setExpand((prev) => !prev);
  };

  return (
    <>
      {expand ? (
        <div className={styles.expand} onClick={handleExpand}>
          {title}
        </div>
      ) : (
        <form className={styles.form} onSubmit={onSubmit}>
          <div className={styles.container}>
            <span className={styles.title}>{`${title} Form`}</span>
            <span className={styles.collapse} onClick={handleExpand}>
              X
            </span>
          </div>
          {children}
        </form>
      )}
    </>
  );
};

export default Form;
