import styles from "./Expense.module.css";

const formatDate = (dateString) => {
  const options = { year: "numeric", month: "long", day: "numeric" };
  const date = new Date(dateString);
  return date.toLocaleDateString("en-US", options);
};

const Expense = (props) => {
  // Get the month from expenseDate
  const month = props.expenseDate.substring(5, 7);

  // Define colors for each month (customize as needed)
  const monthColors = [
    "#FFA07A", // Light Salmon (January)
    "#FFD700", // Gold (February)
    "#98FB98", // Pale Green (March)
    "#87CEFA", // Light Sky Blue (April)
    "#FFD700", // Gold (May)
    "#FF69B4", // Hot Pink (June)
    "#87CEEB", // Sky Blue (July)
    "#FFA500", // Orange (August)
    "#DDA0DD", // Plum (September)
    "#8B4513", // Saddle Brown (October)
    "#2E8B57", // Sea Green (November)
    "#8A2BE2", // Blue Violet (December)
  ];
  const expenseStyle = {
    backgroundColor: `${monthColors[(month % monthColors.length) - 1]}`,
  };

  return (
    <div className={styles.container} style={expenseStyle}>
      <div>
        <div>{props.name}</div>
        <div>{formatDate(props.expenseDate)}</div>
      </div>
      <div className={styles.amount}>${props.amount}</div>
    </div>
  );
};

export default Expense;
