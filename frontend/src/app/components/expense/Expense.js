import styles from "./Expense.module.css";

const Expense = (props) => {
  return (
    <div className={styles.container}>
      <div>
        <div>{props.name}</div>
        <div>{props.expenseDate}</div>
      </div>
      <div>{props.amount}</div>
    </div>
  );
};

export default Expense;
