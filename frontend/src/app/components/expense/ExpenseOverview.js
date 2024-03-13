import Chart from "../chart/Charts";
import styles from "./ExpenseOverview.module.css";

const ExpenseOverview = (props) => {
  let total = 0;
  props.clanExpenses.forEach((element) => {
    total += element.amount;
  });

  let monthlyTotals = {
    "01": 0,
    "02": 0,
    "03": 0,
    "04": 0,
    "05": 0,
    "06": 0,
    "07": 0,
    "08": 0,
    "09": 0,
    10: 0,
    11: 0,
    12: 0,
  };

  props.clanExpenses.forEach((exp) => {
    const _month = exp.expenseDate.slice(5, 7);
    monthlyTotals[_month] += exp.amount;
  });

  const currentDate = new Date().toLocaleDateString();
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
      <div>Current Date: {currentDate}</div>
      <div>
        {monthString[monthDigit]} Total: ${total}
      </div>
      <Chart id="420" data={Object.values(monthlyTotals)}></Chart>
    </div>
  );
};

export default ExpenseOverview;
