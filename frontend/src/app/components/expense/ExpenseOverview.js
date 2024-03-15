import { useAuth } from "../context/Context";
import Chart from "../chart/Charts";
import styles from "./ExpenseOverview.module.css";

const ExpenseOverview = (props) => {
  const { membersList, members } = useAuth();
  
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

  // maintain calendar order
  const order = [
    "01",
    "02",
    "03",
    "04",
    "05",
    "06",
    "07",
    "08",
    "09",
    "10",
    "11",
    "12",
  ];
  let _monthlyTotals = [];
  order.forEach((month) => {
    _monthlyTotals.push(monthlyTotals[month]);
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

  const owed = _monthlyTotals[monthDigit] / members.length;

  return (
    <div className={styles.container}>
      <div className={styles.info}>
        <div>
          <div>Current Date: {currentDate}</div>
          <div>
            {monthString[monthDigit]} Total: ${_monthlyTotals[monthDigit]}
          </div>
        </div>
        <div className={styles.owed}>${owed.toFixed(2)}</div>
      </div>
      <Chart id="420" data={_monthlyTotals}></Chart>
    </div>
  );
};

export default ExpenseOverview;
