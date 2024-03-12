import styles from "./ExpenseOverview.module.css";

const ExpenseOverview = (props) => {
  console.log("wud upp: ", props.clanExpenses);

  let total = 0;
  props.clanExpenses.forEach((element) => {
    total += element.amount;
  });

  const currentDate = new Date().toJSON().slice(0, 10);
  const monthDigit = new Date().getMonth();
  const monthString = [
    "Jan",
    "Feb",
    "March",
    "April",
    "May",
    "June",
    "July",
    "Aug",
    "Sept",
    "Oct",
    "Nov",
    "Dec",
  ];

  console.log("total is: ", total);
  return (
    <div className={styles.container}>
      <div>{currentDate}</div>
      <div>
        {monthString[monthDigit]} Total: $ {total}
      </div>
    </div>
  );
};

export default ExpenseOverview;
