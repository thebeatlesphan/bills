import { useState, useRef } from "react";
import styles from "./InputField.module.css";

const InputField = ({ label, type, onChange, ...props }) => {
  const [value, setValue] = useState("");
  const inputRef = useRef(null);

  const handleChange = (e) => {
    const newValue = e.target.value;
    console.log(newValue);
    setValue(newValue);

    if (onChange) {
      onChange(e.target.value);
    }
  };

  const clearInput = () => {
    setValue("");
    if (inputRef.current) {
      inputRef.current.value = "";
    }
  };

  return (
    <div className={styles.input}>
      <label>{label}</label>
      <input
        type={type}
        onChange={handleChange}
        value={value}
        ref={inputRef}
        {...props}
      ></input>
    </div>
  );
};

export default InputField;
