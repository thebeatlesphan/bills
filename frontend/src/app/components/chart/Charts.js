import { Bar } from "react-chartjs-2";
import Chart from "chart.js/auto";

const Charts = (props) => {
  const data = {
    labels: [
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
    ],
    datasets: [
      {
        label: "Year Overview",
        data: props.data,
        backgroundColor: [
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
        ],
        borderColor: [
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
        ],
        borderWidth: 1,
      },
    ],
  };

  return (
    <div>
      <Bar
        data={data}
        width={400}
        height={200}
        options={{
          maintainAspectRatio: false,
        }}
      />
    </div>
  );
};

export default Charts;
