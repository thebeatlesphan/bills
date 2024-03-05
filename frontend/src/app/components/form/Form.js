import { useState } from "react";
import styles from "./Form.module.css";

const Form = ({ onSubmit, title, children, ...props }) => {
  const [expand, setExpand] = useState(true);

  const handleExpand = () => {
    setExpand((prev) => !prev);
  };

  return (
    <>
      {expand ? (
        <div className={styles.expand} onClick={handleExpand}>
          {`Add ${title}`}
        </div>
      ) : (
        <form className={styles.form} onSubmit={onSubmit}>
          <div className={styles.collapse} onClick={handleExpand}>
            X
          </div>
          <div className={styles.title}>{`${title} Form`}</div>
          {children}
        </form>
      )}
    </>
  );
};

export default Form;
