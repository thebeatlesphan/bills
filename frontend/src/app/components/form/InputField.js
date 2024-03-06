import { useState } from "react";
import styles from "./InputField.module.css";

const InputField = ({ label, type, onChange, value, ...props }) => {

  return (
    <div className={styles.input}>
      <label>{label}</label>
      <input
        type={type}
        onChange={onChange}
        value={value}
        {...props}
      ></input>
    </div>
  );
};

export default InputField;
