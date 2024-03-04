import styles from "./InputField.module.css";

const InputField = ({ label, type, onChange, ...props }) => {
  return (
    <div className={styles.input}>
      <label>{label}</label>
      <input type={type} onChange={onChange} {...props}></input>
    </div>
  );
};

export default InputField;
