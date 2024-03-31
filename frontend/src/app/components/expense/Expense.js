import styles from "./Expense.module.css";
import React, { useRef } from "react";
import Button from "../button/Button";
import { useAuth } from "../context/Context";

const formatDate = (dateString) => {
  const options = {
    year: "numeric",
    month: "long",
    day: "numeric",
    timeZone: "UTC",
  };
  const date = new Date(dateString);
  return date.toLocaleDateString("en-US", options);
};

const Expense = (props) => {
  const { deleteExpense } = useAuth();

  const expenseRef = useRef();

  const showModal = () => {
    expenseRef.current.showModal();
  };

  const closeModal = () => {
    expenseRef.current.close();
  };

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
    backgroundColor: `${monthColors[month - 1]}`,
  };

  const handleDelete = async () => {
    const url = `${process.env.NEXT_PUBLIC_API}api/expense/delete`;
    const token = sessionStorage.getItem("jwtToken");
    const data = { expenseId: props.id };

    const response = await fetch(url, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    const reply = await response.json();
    console.log(reply);
    console.log(props);
    if (!response.ok) {
      window.alert(reply.message);
    } else {
      deleteExpense(props);
    }
  };

  return (
    <>
      <div
        className={styles.container}
        style={expenseStyle}
        onClick={showModal}
      >
        <div>
          <div>{props.name}</div>
          <div>{formatDate(props.expenseDate)}</div>
        </div>
        <div className={styles.amount}>${props.amount.toFixed(2)}</div>
      </div>

      <dialog className={styles.expenseDialog} ref={expenseRef}>
        <div className={styles.expenseContainer}>
          <div value={props.id} onClick={handleDelete}>
            {props.id}
          </div>
          <Button label="Close" onClick={closeModal} />
        </div>
      </dialog>
    </>
  );
};

export default Expense;
